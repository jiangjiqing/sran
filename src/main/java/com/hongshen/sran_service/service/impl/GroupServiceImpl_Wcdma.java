package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.*;
import com.hongshen.sran_service.entity.UnicomAlarmWcdma;
import com.hongshen.sran_service.service.GroupService;
import com.hongshen.sran_service.service.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupServiceImpl_Wcdma implements GroupService{

    @Autowired
    private UnicomAuthorityWcdmaMapper authorityWcdmaMapper;

    @Autowired
    private UnicomGroupWcdmaMapper groupWcdmaMapper;

    @Autowired
    private UnicomQuotaHistoryGroupWcdmaMapper quotaWcdmaMapper;

    @Autowired
    private UnicomFavoriteWcdmaMapper favoriteWcdmaMapper;

    @Autowired
    private UnicomAlarmWcdmaMapper alarmWcdmaMapper;

    @Override
    public List<JSONObject> getGroups(String userName, String time) {

        List<JSONObject> results = new ArrayList<>();

        String authList = null;

        authList = authorityWcdmaMapper.getAuthorityList(userName);

        if (authList != null) {

            String [] list = authList.split(",");

            for (String node : list) {

                JSONObject result = quotaWcdmaMapper.getHistoryData(node, time);

                if (!result.isEmpty()) {

                    results.add(result);
                }
            }

        } else {

            return results;
        }

        return results;
    }

    @Override
    public JSONObject getGroupsAlarm() {

        JSONObject result = new JSONObject();

        List<UnicomAlarmWcdma> alarmWcdmas = new ArrayList<>();

        String generation = Constants.WCDMA;

        alarmWcdmas = alarmWcdmaMapper.getAllAlarmWcdma();

        if (!alarmWcdmas.isEmpty()) {

            result.put("generation", generation);
            result.put("alarms", alarmWcdmas);

            return result;
        }

        return result;
    }
}
