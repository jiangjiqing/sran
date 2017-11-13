package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.UnicomAlarmWcdmaMapper;
import com.hongshen.sran_service.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by poplar on 11/13/17.
 */
@Service
public class AlarmService_Unicom_Lte implements AlarmService {
    @Autowired
    private UnicomAlarmWcdmaMapper unicomAlarmWcdmaMapper;
    @Override
    public List<JSONObject> getAllAlarmSerice() {
        return null;
    }

    @Override
    public Map<String, Object> getGroupAlarmInfo() {
        return unicomAlarmWcdmaMapper.getGroupAlarmInfo();
    }
}
