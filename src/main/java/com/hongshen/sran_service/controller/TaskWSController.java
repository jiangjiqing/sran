package com.hongshen.sran_service.controller;
import com.hongshen.sran_service.service.util.websocket.HttpSessionConfigurator;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
//该注解用来指定一个URI，客户端可以通过这个URI来连接到WebSocket。类似Servlet的注解mapping。无需在web.xml中配置。
@ServerEndpoint(value = "/websockets", configurator = HttpSessionConfigurator.class )
@Component
public class TaskWSController {

    private Session session;

    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session){
        System.out.println("open");
        this.session = session;
        requstSoxket(null);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
                   //在线数减1
        System.out.println("连接关闭！");
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(final String message, final Session session) throws IOException {
        requstSoxket(message);
    }
    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
        this.session.getBasicRemote().sendText(message);
    }

    public void requstSoxket(String msg){
        if(msg == null){
            msg = "";
        }
        System.out.println(msg);
        try {
            Process ss = Runtime.getRuntime().exec("ping 192.168.0.129");
            StringBuffer out = new StringBuffer();
            byte[] b = new byte[4096];
            for (int n; (n = ss.getInputStream().read(b)) != -1; ) {
                //out.append(new String(b, 0, n));
                this.sendMessage(new String(b, 0, n));
                System.out.println(out.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

