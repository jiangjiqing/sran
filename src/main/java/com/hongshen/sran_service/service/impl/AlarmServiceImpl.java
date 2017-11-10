package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.UnicomAlarmLteMapper;
import com.hongshen.sran_service.dao.UnicomAlarmWcdmaMapper;
import com.hongshen.sran_service.entity.UnicomAlarmLte;
import com.hongshen.sran_service.entity.UnicomAlarmWcdma;
import com.hongshen.sran_service.service.AlarmService;
import com.hongshen.sran_service.service.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlarmServiceImpl implements AlarmService{

    @Autowired
    private UnicomAlarmWcdmaMapper alarmWcdmaMapper;

    @Autowired
    private UnicomAlarmLteMapper alarmLteMapper;

    @Override
    public List<JSONObject> getAllAlarmSerice() {

        List<JSONObject> resultList = new ArrayList<>();

        JSONObject resultWcdma = new JSONObject();
        JSONObject resultLte = new JSONObject();

        List<UnicomAlarmWcdma> alarmWcdmas = new ArrayList<>();
        List<UnicomAlarmLte> alarmLtes = new ArrayList<>();

        String wcdmaGeneration = Constants.WCDMA;
        String lteGeneration = Constants.LTE;

        alarmWcdmas = alarmWcdmaMapper.getAllAlarmWcdma();

        alarmLtes = alarmLteMapper.getAllAlarmLte();

        if (!alarmWcdmas.isEmpty()) {

            resultWcdma.put("generation", wcdmaGeneration);
            resultWcdma.put("alarms", alarmWcdmas);

            resultList.add(resultWcdma);
        } else if (!alarmLtes.isEmpty()) {

            resultLte.put("generation", lteGeneration);
            resultLte.put("alarms", alarmLtes);

            resultList.add(resultLte);
        }

        return resultList;
    }
}
