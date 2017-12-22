package com.hongshen.sran_service.controller;
import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.service.util.websocket.HttpSessionConfigurator;
import org.springframework.stereotype.Component;
import com.hongshen.sran_service.service.util.Constants;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//该注解用来指定一个URI，客户端可以通过这个URI来连接到WebSocket。类似Servlet的注解mapping。无需在web.xml中配置。
@ServerEndpoint(value = "/sran/service/net/task", configurator = HttpSessionConfigurator.class)
@Component
public class TaskWSController {

    private Session session;

    public static Map<String, Boolean> taskStatusMap = new HashMap<>();
    public static Map<String, Session> taskStatusSession = new HashMap<>();

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
    public void onMessage(String loginName, final Session session) throws IOException {
        //loginName="666";
        JSONObject result = new JSONObject();

        if (loginName == null || loginName.equals("")) {

            result.put("result", "failed");
            result.put("msg", "loginName null");
            this.sendMessage(result.toJSONString());

        }else if(taskStatusMap.get(loginName)!=null&&taskStatusMap.get(loginName)==true){

            result.put("result","failed");
            result.put("msg","task is running");
            this.sendMessage(result.toJSONString());

        }else {

            taskStatusMap.put(loginName, true);
            taskStatusSession.put(loginName, session);
            requestSocket(loginName);
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

    public void requestSocket(String loginName) throws IOException {//TODO

        String mobatchPath = Constants.MOSHELL_ROOT_PATH + "mobatch";
        String siteFilePath = Constants.TASK_ROOT_PATH + loginName + "/" + Constants.TASK_DIR_SITE + "/" + Constants.TASK_FILE_SITE;
        String cmdFilePath = Constants.TASK_ROOT_PATH + loginName + "/" + Constants.TASK_DIR_CMD + "/" + Constants.TASK_FILE_CMD;
        String logFileDir = Constants.TASK_ROOT_PATH + loginName + "/" + Constants.TASK_DIR_LOG;

        File outfile = new File(logFileDir);
        if(!outfile.exists()){
            outfile.mkdir();
        }

        JSONObject result = new JSONObject();

        if(!taskStatusSession.keySet().contains(loginName)||!taskStatusMap.keySet().contains(loginName)){

            result.put("result","failed");
            result.put("msg","server error");

        }else {

            int num=0;

            try {
                Process process = Runtime.getRuntime().exec(mobatchPath + " " + siteFilePath + " " + cmdFilePath + " " + logFileDir);

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                Date date1 = new Date();
                result.put("timeStart",date1);

                String data = null;
                while (taskStatusMap.get(loginName) == true && (data = reader.readLine())!=null) {
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
                   }

                    //TODO delete
                    if(num==50){
                        break;
                    }
                }
                System.out.println(data);
                if(taskStatusMap.get(loginName) == false) {
                    process.destroy();
                }
                taskStatusMap.remove(loginName);
                taskStatusSession.remove(loginName);

            } catch (IOException e) {
                e.printStackTrace();
                this.sendMessage("cuowu");
                taskStatusMap.put(loginName,false);
            }
        }
    }
}