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

//    @Override
//    public List<JSONObject> getAllAlarmSerice() {
//
//        List<JSONObject> resultList = new ArrayList<>();
//
//        JSONObject resultWcdma = new JSONObject();
//        JSONObject resultLte = new JSONObject();
//
//        List<UnicomAlarmWcdma> alarmWcdmas = new ArrayList<>();
//        List<UnicomAlarmLte> alarmLtes = new ArrayList<>();
//
//        String wcdmaGeneration = Constants.WCDMA;
//        String lteGeneration = Constants.LTE;
//
//        alarmWcdmas = alarmWcdmaMapper.getAllAlarmWcdma();
//
//        alarmLtes = alarmLteMapper.getAllAlarmLte();
//
//        if (!alarmWcdmas.isEmpty()) {
//
//            resultWcdma.put("generation", wcdmaGeneration);
//            resultWcdma.put("alarms", alarmWcdmas);
//
//            resultList.add(resultWcdma);
//        } else if (!alarmLtes.isEmpty()) {
//
//            resultLte.put("generation", lteGeneration);
//            resultLte.put("alarms", alarmLtes);
//
//            resultList.add(resultLte);
//        }
//
//        return resultList;
//    }
//
//    @Override
//    public Map<String, Object> getGroupAlarmInfo() {
//        return alarmLteMapper.getGroupAlarmInfo();
//    }
}
