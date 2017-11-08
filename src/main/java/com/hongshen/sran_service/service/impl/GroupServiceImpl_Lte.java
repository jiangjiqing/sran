package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.UnicomFavoriteLteMapper;
import com.hongshen.sran_service.dao.UnicomGroupWcdmaMapper;
import com.hongshen.sran_service.dao.UnicomQuotaHistoryGroupLteMapper;
import com.hongshen.sran_service.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl_Lte implements GroupService{

    @Autowired
    private UnicomGroupWcdmaMapper groupWcdmaMapper;
    @Autowired
    private UnicomQuotaHistoryGroupLteMapper quotaLteMapper;
    @Autowired
    private UnicomFavoriteLteMapper favoriteLteMapper;

    @Override
    public List<JSONObject> getGroups(String userName) {

        return null;
    }
}
