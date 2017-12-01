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
        return nodeMapper.getGroupList();
    }

    @Override
    public List<JSONObject> getNodeListByGroup(String groupName) {
        return nodeMapper.getNodeListByGroup(groupName);
    }

    //    @Override //TODO
//    public List<JSONObject> getNodeInfoList() {
//        return nodeMapper.getNodeInfoList();
//    }

    @Override
    public List<String> getNodeNameListByGroup(String groupName) {
        return nodeMapper.getNodeNameListByGroup(groupName);
    }

    @Override
    public List<JSONObject> getCellListByNode(String nodeName) {
        return cellMapper.getCellListByNode(nodeName);
    }

    @Override
    public List<String> getCellNameListByNode(String nodeName) {
        return cellMapper.getCellNameListByNode(nodeName);
    }

    @Override
    public JSONObject getGroupInfo(String groupName) {
        return null;
    } // tac info is null

    @Override
    public JSONObject getNodeInfo(String nodeName) {
        return nodeMapper.getNodeInfo(nodeName);
    }

    @Override
    public JSONObject getCellInfo(String cellName) {
        return cellMapper.getCellInfo(cellName);
    }

    @Override
    public JSONObject getNodelatitudeAndlongitude(String nodeName) {
        return  nodeMapper.getNodeLaoutAndLong(nodeName);
    }
}
