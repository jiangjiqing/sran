package com.hongshen.sran_service.service;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by poplar on 11/13/17.
 */
public interface TaskService {

    JSONObject getTaskInfo(String loginName);

    Boolean hasOriginalLog(String loginName);

    Boolean hasScriptLog(String loginName);

    int startTask(String loginName, JSONObject param);

    int cacelTask(String loginName);
}
