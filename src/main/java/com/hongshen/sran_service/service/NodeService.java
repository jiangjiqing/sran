package com.hongshen.sran_service.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface NodeService {

    List<JSONObject> getNodes(String userName, String groupName, String time);
}
