package com.hongshen.sran_service.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface GroupService {

    List<JSONObject> getGroups(String userName, String time);

    JSONObject getGroupsAlarm();
}
