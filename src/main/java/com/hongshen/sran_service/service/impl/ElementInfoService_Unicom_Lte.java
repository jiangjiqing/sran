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
public class ElementInfoService_Unicom_Lte implements ElementInfoService {

	@Autowired
    private UnicomProtectLteMapper protectMapper;

	@Autowired
    private UnicomFavoriteLteMapper favoriteMapper;

    @Autowired
    private UnicomGroupWcdmaMapper groupMapper; //3G

	@Autowired
    private UnicomNodeLteMapper nodeMapper;

    @Autowired
    private UnicomCellLteMapper cellMapper;

    @Override
    public List<JSONObject> getProtectList() {
        return protectMapper.getProtectList();
    }

    @Override
    public List<JSONObject> getFavoriteList() {
        return favoriteMapper.getFavoriteList();
    }

    @Override
    public int cancelCollection(String name) {
        return favoriteMapper.cancelCollection(name);
    }

    @Override
    public int addCollection(String name) {
        return favoriteMapper.addCollection(name);
    }


    @Override
    public List<JSONObject> getGroupList() {
        return groupMapper.getGroupList();
    }


    @Override
    public List<JSONObject> getNodeList(String groupName) {
        return nodeMapper.getNodeList(groupName);
    }

    @Override
    public JSONObject getGroupByName(String groupName) {
        return groupMapper.getGroupByName(groupName);
    }


    @Override
    public List<JSONObject> getSpecifiedGroupList(String groupName) {
        return null;//TODO
    }

    @Override
    public List<JSONObject> getSpecifiedNodeList(String nodeName) {
        return nodeMapper.getSpecifiedNodeList(nodeName);
    }

    @Override
    public List<JSONObject> getSpecifiedCellList(String cellName) {
        return cellMapper.getSpecifiedCellList(cellName);
    }

    @Override
    public List<JSONObject> getGroupInfoList() {
        return groupMapper.getGroupInfoList();
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
