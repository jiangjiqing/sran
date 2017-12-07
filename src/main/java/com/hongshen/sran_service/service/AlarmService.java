package com.hongshen.sran_service.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AlarmService {

    List<JSONObject> getAlarmInfoList();

    List<JSONObject> getProtectAlarmInfoList();

    List<JSONObject> getNormalAlarmInfoList();

	List<JSONObject> getGroupAlarmByName(String groupName);

    List<JSONObject> getNodeAlarmByName(String nodeName);

    List<JSONObject> getCellAlarmByName(String cellName);
}
