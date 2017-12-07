package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.UnicomGroupWcdmaMapper;
import com.hongshen.sran_service.dao.UnicomRoomWcdmaMapper;
import com.hongshen.sran_service.service.TelecomRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by poplar on 11/14/17.
 */
@Service
public class TelecomRoomService_Unicom_Wcdma implements TelecomRoomService {

    @Autowired
    private UnicomGroupWcdmaMapper groupMapper;

    @Autowired
    private UnicomRoomWcdmaMapper roomMapper;

    @Override
    public List<JSONObject> getRoomLocationList() {
        return roomMapper.getRoomLocationList();
    }

    @Override
    public List<JSONObject> getRoomList() {
        return groupMapper.getRoomList();
    }

    @Override
    public List<JSONObject> getGroupListByRoom(String roomName) {
        return groupMapper.getGroupListByRoom(roomName);
    }

    @Override
    public List<String> getGroupNameListByRoom(String roomName) {
        return groupMapper.getGroupNameListByRoom(roomName);
    }
}
