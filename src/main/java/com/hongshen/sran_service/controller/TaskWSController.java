package com.hongshen.sran_service.controller;
import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.service.util.FileHelper;
import com.hongshen.sran_service.service.util.websocket.HttpSessionConfigurator;
import org.springframework.stereotype.Component;
import com.hongshen.sran_service.service.util.Constants;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

//该注解用来指定一个URI，客户端可以通过这个URI来连接到WebSocket。类似Servlet的注解mapping。无需在web.xml中配置。
/*@ServerEndpoint(value = "/sran/service/net/task/{param}", configurator = HttpSessionConfigurator.class)*/
@ServerEndpoint(value = "/sran/service/net/task", configurator = HttpSessionConfigurator.class)
@Component
public class TaskWSController {

    private Session session;

    public static Map<String, Boolean> taskStatusMap = new HashMap<>();
    public static Map<String, Session> taskStatusSession = new HashMap<>();
    //private static Set<String> runingTask = new HashSet<>();
    /**
     * 连接建立成功调用的方法
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;

        System.out.println("open");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        System.out.println("连接关闭！");
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param loginName 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(@PathParam(value="param")String param, String loginName, final Session session) throws IOException {
        //loginName="666";
        JSONObject result = new JSONObject();

        if (loginName == null || loginName.equals("")) {

            result.put("result", "failed");
            result.put("msg", "loginName null");
            this.sendMessage(result.toJSONString());

        }else if(taskStatusMap.get(loginName)!=null/*&&runingTask.contains(param)*/&& taskStatusMap.get(loginName)==true){

            result.put("result","failed");
            result.put("msg","task is running");
            this.sendMessage(result.toJSONString());

        }else {

            taskStatusMap.put(loginName, true);
            taskStatusSession.put(loginName, session);
            requestSocket(param,loginName);
        }
    }

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);

    }

    public void requestSocket(@PathParam(value="param") String param,
                              String loginName) throws IOException {//TODO

        String mobatchPath = Constants.MOSHELL_ROOT_PATH + "mobatch";
        String siteFilePath = Constants.TASK_ROOT_PATH + loginName + "/" + Constants.TASK_DIR_SITE + "/" + Constants.TASK_FILE_SITE;
        String cmdFilePath = Constants.TASK_ROOT_PATH + loginName + "/" + Constants.TASK_DIR_CMD + "/" + Constants.TASK_FILE_CMD;
        String logFileDir = Constants.TASK_ROOT_PATH + loginName + "/" + Constants.TASK_DIR_LOG;

        JSONObject result = new JSONObject();

        if(!taskStatusSession.keySet().contains(loginName)||!taskStatusMap.keySet().contains(loginName)){

            result.put("result","failed");
            result.put("msg","server error");

        }else {

            int num=0;

            try {
                 Process process = Runtime.getRuntime().exec(mobatchPath + " " + siteFilePath + " " + cmdFilePath + " " + logFileDir);
               // runingTask.add(param);
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                Date date1 = new Date();
                result.put("timeStart",date1);

                String data = null;
                while (taskStatusMap.get(loginName)!=null&&taskStatusMap.get(loginName) == true && (data = reader.readLine())!=null) {
                    Date date = new Date();
                   if(data.contains("**")) {
                    num++;
                    if(num!=1) {
                        result.put("total", 20);
                        result.put("complete", num-1);
                        result.put("time", date);
                        System.out.println(data);
                        this.sendMessage(String.valueOf(result));
                        }
                       /* if(num==30){
                            process.destroy();
                            break;
                        }*/
                    }
                }

                if(taskStatusMap.get(loginName) !=null && taskStatusMap.get(loginName) == false) {
                    process.destroy();

                }
                //runingTask.remove(param);
                //if(runingTask.size()==0) {
                    taskStatusMap.remove(loginName);
                    taskStatusSession.remove(loginName);
                    Boolean b =  new FileHelper().compressFile1(Constants.TASK_ROOT_PATH+loginName+"/"+Constants.TASK_DIR_ANALYSIS_LOG+"/"+Constants.TASK_FILE_LOG,
                            Constants.TASK_ROOT_PATH+loginName+"/"+Constants.TASK_DIR_LOG);
               // }

            } catch (IOException e) {
                e.printStackTrace();
                taskStatusMap.put(loginName,false);
            }
        }
    }
}