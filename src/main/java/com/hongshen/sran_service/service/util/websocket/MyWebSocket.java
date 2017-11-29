package com.hongshen.sran_service.service.util.websocket;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by poplar on 11/28/17.
 */
@ServerEndpoint(value = "/websocket", configurator = HttpSessionConfigurator.class )
//
@Component
public class MyWebSocket {


    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private HttpSession httpSession;
    private static final AtomicInteger connectionIds = new AtomicInteger(0);
    private static final String GUEST_PREFIX = "Guest";
    private static final Map<String,Object> connections = new HashMap<String,Object>();
    private final String nickname;
    public MyWebSocket(){
        nickname = GUEST_PREFIX + connectionIds.getAndIncrement();     }
    /**
     * 连接成功*/
    @OnOpen
    public void onOpen(Session session,EndpointConfig config) throws IOException ,EncodeException {
        HttpSession httpSession= (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        connections.put(nickname,this);
        System.out.println("onopen");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("onClose"+reason.toString());
    }

    /**
     * 收到消息
     *
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(Session session,String message) throws IOException ,InterruptedException{
        System.out.println("来自浏览器的消息:" + String.valueOf(message));
        session.getBasicRemote().sendText("This is the first server message");
        int sentMessages = 0;

        session.getBasicRemote().sendText("This is the last server message");

    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误,.,.,.,.,.,.,.,.,.,..,.,.");
        error.printStackTrace();
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);//同步
        //this.session.getAsyncRemote().sendText(message);//异步
    }
    public void sendAll(String msg) {
        for (String key : connections.keySet()) {
            MyWebSocket client = null;

            client = (MyWebSocket) connections.get(key);
            synchronized (client) {
                client.session.getAsyncRemote().sendText(msg);
            }
            try {
                client.session.close();
            } catch (IOException e1) {
// Ignore
            }
        }
    }

}
