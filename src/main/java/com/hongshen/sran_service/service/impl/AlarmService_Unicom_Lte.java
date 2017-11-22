package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.UnicomAlarmLteMapper;
import com.hongshen.sran_service.service.AlarmService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created by poplar on 11/13/17.
 */
@Service
public class AlarmService_Unicom_Lte implements AlarmService {
	
    @Autowired
    private UnicomAlarmLteMapper alarmMapper;
	
    @Override
    public List<JSONObject> getAllAlarmInfo() {
        return alarmMapper.getAllAlarmInfo();
    }
	
	@Override
    public List<JSONObject> getGroupAlarmByGroupName(String groupName) {
        return alarmMapper.getGroupAlarmByGroupName(groupName);
    }
	
	@Override
    public List<JSONObject> getNodeAlarmByNodeName(String nodeName) {
        return alarmMapper.getNodeAlarmByNodeName(nodeName);
    }
	
	@Override
    public List<JSONObject> getCellAlarmByCellName(String cellName) {
        return alarmMapper.getCellAlarmByCellName(cellName);
    }
    //@Override
    //public Map<String, Object> getGroupAlarmInfo() {
    //    return alarmMapper.getGroupAlarmInfo();
    //}
	
}
