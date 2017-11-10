package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.*;
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

    @Autowired
    private UnicomFormulaLteMapper formulaLteMapper;

    @Override
    public List<JSONObject> getGroups(String userName, String time) {

        return null;
    }

    @Override
    public List<JSONObject> getGroupAlarmByGroupName(String groupName) {

        List<JSONObject> results = new ArrayList<>();

        results = alarmLteMapper.getGroupAlarmByGroupNameLte(groupName);

        if (!results.isEmpty()) {

            return results;
        }

        return results;
    }

    @Override
    public List<JSONObject> getGroupQuotaByGroupName(String groupName) {


        return null;
    }
}
