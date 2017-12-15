package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.UnicomGroupTaskWcdmaMapper;
import com.hongshen.sran_service.dao.UnicomUserTaskGroupWcdmaMapper;
import com.hongshen.sran_service.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.hongshen.sran_service.controller.TaskWSController.taskStatusMap;

/**
 * Created by poplar on 11/13/17.
 */
@Service
public class TaskService_Unicom_Wcdma implements TaskService {

    @Autowired
    private UnicomGroupTaskWcdmaMapper groupTaskMapper;

    @Autowired
    private UnicomUserTaskGroupWcdmaMapper TaskGroupMapper;

    @Override
    public List<JSONObject> getTaskInfo(String loginName) {
        return TaskGroupMapper.getTaskInfo(loginName);
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
    public int addTask(String loginName, JSONObject param) {
        return TaskGroupMapper.addTask(param);
    }

    @Override
    public int cacelTask(String loginName) {
        taskStatusMap.put(loginName,false);
        return 1;
    }
}
