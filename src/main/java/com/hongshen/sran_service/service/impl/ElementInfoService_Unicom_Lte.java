package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.UnicomFavoriteLteMapper;
import com.hongshen.sran_service.dao.UnicomNodeLteMapper;
import com.hongshen.sran_service.dao.UnicomProtectLteMapper;
import com.hongshen.sran_service.dao.UnicomQuotaHistoryNodeLteMapper;
import com.hongshen.sran_service.entity.UnicomNodeWcdma;
import com.hongshen.sran_service.service.ElementInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created by poplar on 11/13/17.
 */
@Service
public class ElementInfoService_Unicom_Lte implements ElementInfoService {

	@Autowired
    private UnicomProtectLteMapper unicomProtectMapper;

	@Autowired
    private UnicomFavoriteLteMapper unicomFavoriteMapper;

	@Autowired
    private UnicomQuotaHistoryNodeLteMapper unicomQuotaHistoryNodeMapper;

	@Autowired
    private UnicomNodeLteMapper unicomNodeMapper;

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
        return null;
    }

    @Override
    public List<JSONObject> getSpecifiedNodeList(String groupName, String nodeName) {
        return unicomNodeMapper.getSpecifiedNodeList(groupName,nodeName);
    }
//    @Autowired
//    private UnicomProtectLteMapper unicomProtectLteMapper;
//    @Autowired
//    private UnicomGroupWcdmaMapper unicomGroupWcdmaMapper;
//    @Override
//    public List<JSONObject> getGroupInfo(String name) {
//        return unicomGroupWcdmaMapper.getGroupInfo(name);
//    }
//
//    @Override
//    public List<String> getProtect() {
//
//        return unicomProtectLteMapper.getProtect();
//    }
}
