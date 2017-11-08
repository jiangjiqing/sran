package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.UnicomFavoriteWcdmaMapper;
import com.hongshen.sran_service.dao.UnicomGroupWcdmaMapper;
import com.hongshen.sran_service.dao.UnicomQuotaHistoryGroupWcdmaMapper;
import com.hongshen.sran_service.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl_Wcdma implements GroupService{

    @Autowired
    private UnicomGroupWcdmaMapper groupWcdmaMapper;
    @Autowired
    private UnicomQuotaHistoryGroupWcdmaMapper quotaWcdmaMapper;
    @Autowired
    private UnicomFavoriteWcdmaMapper favoriteWcdmaMapper;

    @Override
    public List<JSONObject> getGroups(String userName) {


        return null;
    }
}
