package com.hongshen.sran_service.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.UnicomAlarmIndexMapper;
import com.hongshen.sran_service.dao.UnicomAlarmLibraryWcdmaMapper;
import com.hongshen.sran_service.service.AlarmLibService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by poplar on 11/13/17.
 */
@Service
public class AlarmLibService_Unicom_Wcdma implements AlarmLibService {

    @Autowired
    private UnicomAlarmLibraryWcdmaMapper alarmLibMapper;

    @Autowired
    private UnicomAlarmIndexMapper alarmIndexMapper;

    @Override
    public List<JSONObject> getAlarmList() {
        return alarmLibMapper.getAlarmList();
    }

    @Override
    public JSONObject getAlarmByName(String alarmName){
        return alarmLibMapper.getAlarmByName(alarmName);
    }

    @Override
    public int updateAlarmByName(String alarmName, JSONObject param){
        return alarmLibMapper.updateAlarmByName(alarmName,param);
    }

    @Override
    public int addAlarm(JSONObject param){
        return 0;//TODO //add index and lib
    }

    @Override
    public int deleteAlarmByName(String alarmName){
        return 0;//TODO
    }
}
