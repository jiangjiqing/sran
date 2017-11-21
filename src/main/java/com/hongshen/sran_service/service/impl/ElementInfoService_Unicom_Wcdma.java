package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.*;
import com.hongshen.sran_service.entity.UnicomNodeWcdma;
import com.hongshen.sran_service.service.ElementInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created by poplar on 11/13/17.
 */
@Service
public class ElementInfoService_Unicom_Wcdma implements ElementInfoService {
	
    @Autowired
    private UnicomProtectWcdmaMapper unicomProtectMapper;

    @Autowired
    private UnicomFavoriteWcdmaMapper unicomFavoriteMapper;

    @Autowired
    private UnicomQuotaHistoryNodeWcdmaMapper unicomQuotaHistoryNodeMapper;

    @Autowired
    private UnicomGroupWcdmaMapper unicomGroupMapper;

    @Autowired
    private UnicomNodeWcdmaMapper unicomNodeMapper;

    @Override
    public List<JSONObject> getProtectList() {
        return unicomProtectMapper.getProtectList();
    }

    @Override
    public List<JSONObject> getFavoriteList() {
        return unicomFavoriteMapper.getFavoriteList();
    }

    @Override
    public int getLevelByName(String nodeName) {
        return unicomQuotaHistoryNodeMapper.getLevelByName(nodeName);
    }

    @Override
    public int cancelCollection(String name) {
        return unicomFavoriteMapper.cancelCollection(name);
    }

    @Override
    public int addCollection(String name) {
        return unicomFavoriteMapper.addCollection(name);
    }

    @Override
    public List<JSONObject> getSpecifiedGroupList(String groupName) {
        return unicomGroupMapper.getSpecifiedGroupList(groupName);
    }

    @Override
    public List<JSONObject> getSpecifiedNodeList(String groupName, String nodeName) {
        return unicomNodeMapper.getSpecifiedNodeList(groupName,nodeName);
    }
//    @Autowired
//    private UnicomGroupWcdmaMapper unicomGroupWcdmaMapper;
//    @Autowired
//    private UnicomProtectWcdmaMapper unicomProtectWcdmaMapper;
//    @Override
//    public List<JSONObject> getGroupInfo(String name) {
//        return unicomGroupWcdmaMapper.getGroupInfo(name);
//    }
//
//    @Override
//    public List<String> getProtect() {
//
//        return unicomProtectWcdmaMapper.getProtect();
//    }
}
