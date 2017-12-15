package com.hongshen.sran_service.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by poplar on 11/13/17.
 */
public interface TaskService {

    List<JSONObject> getTaskInfo(String loginName);

    Boolean hasOriginalLog(String loginName);

    Boolean hasScriptLog(String loginName);

    int addTask(String loginName, JSONObject param);

    int cacelTask(String loginName);
}
