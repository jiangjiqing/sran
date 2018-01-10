package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.*;
import com.hongshen.sran_service.service.ElementInfoService;
import com.hongshen.sran_service.service.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by poplar on 11/13/17.
 */
@Service
public class ElementInfoService_Unicom_Lte implements ElementInfoService {

    @Autowired
    private CacheService_Unicom_Lte cacheService;

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

    @Autowired
    private UnicomNodeInfoIndexLteMapper nodeInfoIndexMapper;

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
        String time = cacheService.getUpdateTimeForQuotaData();
        if (time == null || time.length() == 0){
            return null;
        }else {
            return nodeMapper.getGroupList(time);
        }
    }

    @Override
    public List<JSONObject> getGroupInfoList() {
        //return nodeMapper.getGroupInfoList(); // 4g no group info
        return null;
    }

    @Override
    public List<String> getGroupNameList() {
        return nodeMapper.getGroupNameList();
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
        return null; // tac info is null
    }

    @Override
    public ArrayList<String[]> getGroupInfoProcessed(String groupName) {
        return null; // tac info is null
    }

    @Override
    public JSONObject getNodeInfo(String nodeName) {
        return nodeMapper.getNodeInfo(nodeName);
    }

    @Override
    public ArrayList<String[]> getNodeInfoProcessed(String nodeName) {

        ArrayList<String[]> result = new ArrayList<>();
        JSONObject infos = nodeMapper.getNodeInfo(nodeName);

        for(String key : infos.keySet()) {

            String value = infos.getString(key);

            if (value == null || value.trim().length() <= 0) {
                value = Constants.ELEMENT_INFO_INVALID;
            }

            result.add(new String[]{key, value});
        }
        return result;
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
    public Integer getNodeNum(String tableName, String groupName) {
        return favoriteMapper.getNodeNum(tableName,groupName);
    }

    @Override
    public Integer getNodeofNull(String tableName, String nodeName) {
        return favoriteMapper.getNodeofNull(tableName,nodeName);
    }

    @Override
    public Integer addProdectNode(String nodeName) {
        return protectMapper.addProdectNode(nodeName);
    }

    @Override
    public Integer clearNodes() {
        return protectMapper.clearNodes();
    }

    @Override
    public List<JSONObject> getProtectListnodeName() {
        return protectMapper.getProtectListnodeName() ;
    }

    @Override
    public Integer updateStationName(JSONObject jsonObject) {
        return nodeMapper.updateStationName(jsonObject);
    }

    @Override
    public Integer addRnc(JSONObject jsonObject) {
        return null;
    }

    @Override
    public Integer deleteGroup() {
        return null;
    }

    @Override
    public Integer getGroupCounter() {
        return null;
    }

    @Override
    public int createTable(String tableName) {
        return favoriteMapper.createTable(tableName);
    }

    @Override
    public int addFavoriteFile(String tableName, List paramList) {
        return favoriteMapper.addFavoriteFile(tableName, paramList);
    }

    @Override
    public JSONObject getTable(String tableNameLike) {
        return favoriteMapper.getTable(tableNameLike);
    }

    @Override
    public List<JSONObject> getFavoriteNodes(String tableName,String name) {
        return favoriteMapper.getNodes(tableName,name);
    }

    @Override
    public int deleteFavoriteNode(String tableName, String name) {
        return favoriteMapper.deleteNode(tableName,name);
    }

    @Override
    public int deleteFavoriteNodes(String tableName, String name) {
        return favoriteMapper.deleteNodes(tableName,name);
    }

    @Override
    public int addFavoriteNode(String tableName, String name) {
        return favoriteMapper.addNode(tableName,name);
    }

    @Override
    public int addFavoriteNodes(String tableName, List<JSONObject> nodeNames) {
        return favoriteMapper.addNodes(tableName,nodeNames);
    }

    @Override
    public List<JSONObject> getProtectInfo() {
        return protectMapper.getProtectInfo();
    }

    @Override
    public List<JSONObject> getNodeCountByname(String name) {
        return nodeMapper.getNodeCountByname(name);
    }

    @Override
    public List<JSONObject> getNodeCountByNodeName(String name) {
        return nodeMapper.getNodeCountByNodeName(name);
    }

    @Override
    public JSONObject getGroupIdByName(String name) {
        return groupMapper.getGroupIdByName(name);
    }

    @Override
    public List<String> getVipNodeNameListByGroup(String groupName) {
        return nodeMapper.getVipNodeNameListByGroup(groupName);
    }


}
