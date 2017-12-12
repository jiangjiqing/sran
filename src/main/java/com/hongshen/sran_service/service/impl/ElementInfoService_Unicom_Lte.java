package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.*;
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
        return nodeMapper.getGroupList();
    }

    @Override
    public List<JSONObject> getGroupInfoList() {
        return nodeMapper.getGroupInfoList();
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
    public List<String> getNodeNameList() {
        return nodeMapper.getNodeNameList();
    }

    @Override
    public List<JSONObject> getCellInfoList() {
        return cellMapper.getCellInfoList();
    }

    @Override
    public List<String> getCellNameList() {
        return cellMapper.getCellNameList();
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
    public JSONObject getNodeLocation(String nodeName) {
        return  nodeMapper.getNodeLocation(nodeName);
    }

    @Override
    public List<JSONObject> getNodeLocationsByGroup(String groupName) {
        return nodeMapper.getNodeLocationsByGroup(groupName);
    }

    @Override
    public List<String> getOssNameList() {
        return null;
    } // 4g no oss info

    @Override
    public JSONObject getGroupInfoTable(String tableNameLike) {
        return favoriteMapper.getGroupTable(tableNameLike);
    }

    @Override
    public Integer getNodeNum(String tableName, String groupName) {
        return favoriteMapper.getNodeNum(tableName,groupName);
    }

    @Override
    public JSONObject getNodeInfoTable(String tableNameLike) {
        return favoriteMapper.geNodeTable(tableNameLike);
    }

    @Override
    public Integer getNodeofNull(String tableName, String nodeName) {
        return favoriteMapper.getNodeofNull(tableName,nodeName);
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
