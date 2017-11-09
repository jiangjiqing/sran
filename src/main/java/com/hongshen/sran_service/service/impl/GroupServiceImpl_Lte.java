package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.UnicomAlarmLteMapper;
import com.hongshen.sran_service.dao.UnicomFavoriteLteMapper;
import com.hongshen.sran_service.dao.UnicomGroupWcdmaMapper;
import com.hongshen.sran_service.dao.UnicomQuotaHistoryGroupLteMapper;
import com.hongshen.sran_service.entity.UnicomAlarmLte;
import com.hongshen.sran_service.entity.UnicomAlarmWcdma;
import com.hongshen.sran_service.service.GroupService;
import com.hongshen.sran_service.service.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupServiceImpl_Lte implements GroupService{

    @Autowired
    private UnicomGroupWcdmaMapper groupWcdmaMapper;

    @Autowired
    private UnicomQuotaHistoryGroupLteMapper quotaLteMapper;

    @Autowired
    private UnicomFavoriteLteMapper favoriteLteMapper;

    @Autowired
    private UnicomAlarmLteMapper alarmLteMapper;

    @Override
    public List<JSONObject> getGroups(String userName, String time) {

        return null;
    }

    @Override
    public JSONObject getGroupsAlarm() {

        JSONObject result = new JSONObject();

        List<UnicomAlarmLte> alarmLtes = new ArrayList<>();

        String generation = Constants.LTE;

        alarmLtes = alarmLteMapper.getAllAlarmLte();

        if (!alarmLtes.isEmpty()) {

            result.put("generation", generation);
            result.put("alarms", alarmLtes);

            return result;
        }

        return result;
    }
}
