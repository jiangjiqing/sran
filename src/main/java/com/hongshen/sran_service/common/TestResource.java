package com.hongshen.sran_service.common;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.service.util.NetObjFactory;
import com.hongshen.sran_service.controller.NoticeWSController;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by poplar on 11/23/17.
 */


@Path("/test")
public class TestResource extends BaseController{
    @Autowired
    private NoticeWSController myWebSocket;
    @Autowired
    private NetObjFactory objFactory;
//    @Autowired
//    private Httpclient httpclient;
    @GET
    @Path("/1")
    public String test1(){
        JSONObject json = new JSONObject();

        json.put("c","c");
//        myWebSocket.sendAll(String.valueOf(json));
//        WebSocketSession session = new WebSocketSession();
//        System.out.println(session.getAttribute(arg0))
//        try {
////            countWebSocketHandler.afterConnectionEstablished(session);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        webSocketTest.onMessage();



        return "nnn";
    }

}


