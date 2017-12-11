package com.hongshen.sran_service.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by poplar on 11/14/17.
 */
public interface TelecomRoomService {

    List<JSONObject> getRoomLocationList();

    List<JSONObject> getRoomList();

    List<String> getRoomNameList();

    List<JSONObject> getGroupListByRoom(String roomName);

    List<String> getGroupNameListByRoom(String roomName);
}
