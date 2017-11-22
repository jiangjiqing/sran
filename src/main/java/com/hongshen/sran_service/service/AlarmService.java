package com.hongshen.sran_service.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AlarmService {

    List<JSONObject> getAllAlarmInfo();
	List<JSONObject> getGroupAlarmByGroupName(String groupName);
    List<JSONObject> getNodeAlarmByNodeName(String nodeName);
    List<JSONObject> getCellAlarmByCellName(String cellName);
//    List<JSONObject> getAllAlarmSerice();
//    Map<String,Object> getGroupAlarmInfo();
}
