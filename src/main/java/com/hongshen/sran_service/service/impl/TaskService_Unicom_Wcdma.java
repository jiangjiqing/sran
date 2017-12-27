package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.UnicomGroupTaskWcdmaMapper;
import com.hongshen.sran_service.dao.UnicomUserTaskGroupWcdmaMapper;
import com.hongshen.sran_service.service.TaskService;
import com.hongshen.sran_service.service.util.Constants;
import com.hongshen.sran_service.service.util.FileHelper;
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
    private UnicomUserTaskGroupWcdmaMapper taskGroupMapper;

    @Override
    public List<JSONObject> getTaskList(String loginName) {
        return taskGroupMapper.getTaskList(loginName);
    }

    @Override
    public JSONObject getTaskInfo(String loginName) {
        return taskGroupMapper.getTaskInfo(loginName);
    }

    @Override
    public int addTask(String loginName, JSONObject param) {
        return taskGroupMapper.addTask(loginName, param);
    }

    @Override
    public int cacelTask(String loginName) {
        taskStatusMap.put(loginName,false);
        return 1;
    }

    @Override
    public String getLogPath(String loginName) {
        String filePath = Constants.TASK_ROOT_PATH + loginName
                + "/" + Constants.TASK_DIR_LOG + "/" + Constants.TASK_FILE_LOG;

        if (FileHelper.isExist(filePath)){
            return filePath;

        }else {
            return "";
        }
    }

    @Override
    public String getAnalysisLogPath(String loginName) {
        String filePath = Constants.TASK_ROOT_PATH + loginName
                + "/" + Constants.TASK_DIR_ANALYSIS_LOG + "/" + Constants.TASK_FILE_ANALYSIS_LOG;

        if (FileHelper.isExist(filePath)){
            return filePath;

        }else {
            return "";
        }
    }

    @Override
    public Boolean hasAnalysisScript(String loginName) {
        return null;
    }
}
