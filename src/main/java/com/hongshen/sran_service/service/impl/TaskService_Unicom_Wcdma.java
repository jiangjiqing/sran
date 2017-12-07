package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.UnicomGroupTaskWcdmaMapper;
import com.hongshen.sran_service.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by poplar on 11/13/17.
 */
@Service
public class TaskService_Unicom_Wcdma implements TaskService {

    @Autowired
    private UnicomGroupTaskWcdmaMapper groupTaskMapper;

    @Override
    public JSONObject getTaskInfo(String loginName) {
        return groupTaskMapper.getTaskInfo(loginName);
    }

    @Override
    public Boolean hasOriginalLog(String loginName) {
        //TODO :check file path
        return false;
    }

    @Override
    public Boolean hasScriptLog(String loginName) {
        //TODO :check file path
        return false;
    }

    @Override
    public int startTask(String loginName, JSONObject param) {
        //TODO : add or update, start task
        return 0;
    }

    @Override
    public int cacelTask(String loginName) {
        //TODO : break thread
        return 0;
    }
}
