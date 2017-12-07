package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.*;
import com.hongshen.sran_service.service.ElementInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.util.List;

/**
 * Created by poplar on 11/13/17.
 */
@Service
public class ElementInfoService_Unicom_Wcdma implements ElementInfoService {
	
    @Autowired
    private UnicomProtectWcdmaMapper protectMapper;

    @Autowired
    private UnicomFavoriteWcdmaMapper favoriteMapper;

    @Autowired
    private UnicomGroupWcdmaMapper groupMapper;

    @Autowired
    private UnicomNodeWcdmaMapper nodeMapper;

    @Autowired
    private UnicomCellWcdmaMapper cellMapper;

    @Override
    public List<JSONObject> getProtectList() {
        return protectMapper.getProtectList();
    }

    @Override
    public List<JSONObject> getFavoriteList(String TableName) {
        return favoriteMapper.getFavoriteList(TableName);
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
    public List<JSONObject> getGroupInfoList() {
        return groupMapper.getGroupInfoList();
    }

    @Override
    public List<JSONObject> getNodeListByGroup(String groupName) {
        return nodeMapper.getNodeListByGroup(groupName);
    }

    @Override
    public List<JSONObject> getNodeInfoList() {
        return nodeMapper.getNodeInfoList();
    }

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
        return groupMapper.getGroupInfo(groupName);
    }

    @Override
    public JSONObject getNodeInfo(String nodeName) {
        return nodeMapper.getNodeInfo(nodeName);
    }

    @Override
    public JSONObject getCellInfo(String cellName) {
        return cellMapper.getCellInfo(cellName);
    }

    @Override
    public JSONObject getNodeLocation(String nodeName) {
        return nodeMapper.getNodeLocation(nodeName);
    }

    @Override
    public JSONObject getTable(String gettableName) {
        return favoriteMapper.getTable(gettableName);
    }

    @Override
    public int deleteNode(String tableName, String name) {
        return favoriteMapper.deleteNode(tableName,name);
    }

    @Override
    public List<JSONObject> getNodes(String tableName,String name) {
        return nodeMapper.getNodes(tableName,name);
    }

    @Override
    public int deleteNodes(String tableName, String name) {
        return favoriteMapper.deleteNodes(tableName,name);
    }

    @Override
    public int addNode(String tableName, String name) {
        return favoriteMapper.addNode(tableName,name);
    }

    @Override
    public int addNodes(String tableName, List<JSONObject> nodeNames) {
        return favoriteMapper.addNodes(tableName,nodeNames);
    }


}
