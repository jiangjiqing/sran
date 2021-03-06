package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.UnicomAlarmIndexMapper;
import com.hongshen.sran_service.dao.UnicomAlarmWcdmaMapper;
import com.hongshen.sran_service.service.AlarmService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AlarmService_Unicom_Wcdma implements AlarmService{

    @Autowired
    private UnicomAlarmIndexMapper alarmIndexMapper;

    @Autowired
    private UnicomAlarmWcdmaMapper alarmMapper;

    @Override
    public List<JSONObject> getAlarmInfoList() {
        return alarmMapper.getAlarmInfoList();
    }

    @Override
    public List<JSONObject> getProtectAlarmInfoList() {
        return alarmMapper.getProtectAlarmInfoList();
    }

    @Override
    public List<JSONObject> getNormalAlarmInfoList() {
        return alarmMapper.getNormalAlarmInfoList();
    }

    @Override
    public List<JSONObject> getGroupAlarmByName(String groupName) {
        return alarmMapper.getGroupAlarmByName(groupName);
    }
	
	@Override
    public List<JSONObject> getNodeAlarmByName(String nodeName) {
        return alarmMapper.getNodeAlarmByName(nodeName);
    }
	
	@Override
    public List<JSONObject> getCellAlarmByName(String cellName) {
        return alarmMapper.getCellAlarmByName(cellName);
    }

    @Override
    public List<JSONObject> getAlarmInfoList1(String level) {
        return alarmMapper.getAlarmInfoList1(level);
    }

    @Override
    public List<JSONObject> getAlarmByName(String alarmNameId, String alarmName) {
        return alarmIndexMapper.getAlarmByName(alarmNameId,alarmName);
    }

    @Override
    public JSONObject getAlarmById(String alarmNameId) {
        return alarmIndexMapper.getAlarmById(alarmNameId);
    }

    @Override
    public int addAlarmIndex(JSONObject jsonObject) {
        return alarmIndexMapper.addAlarmIndex(jsonObject);
    }


}
