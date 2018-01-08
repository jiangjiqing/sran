package com.hongshen.sran_service.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by poplar on 11/13/17.
 */
public interface AlarmLibService {

    List<JSONObject> getAlarmList();

    JSONObject getAlarmByName(String alarmName);

    int updateAlarmByName(String alarmName, JSONObject param);

    int addAlarmIndex(JSONObject param);

    int addAlarm(JSONObject param);

    int deleteAlarmByName(String alarmName);

    List<JSONObject> getAlarmInfoList();

    int addAlarms(JSONArray importJson);
}
