package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.UnicomAlarmWcdmaMapper;
import com.hongshen.sran_service.service.AlarmService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AlarmService_Unicom_Wcdma implements AlarmService{

    @Autowired
    private UnicomAlarmWcdmaMapper alarmMapper;

    @Override
    public List<JSONObject> getAllAlarmInfo() {
        return alarmMapper.getAllAlarmInfo();
    }

	@Override
    public JSONObject getGroupAlarmByName(String groupName) {
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
}
