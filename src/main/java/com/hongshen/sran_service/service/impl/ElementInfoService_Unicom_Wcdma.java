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
public class ElementInfoService_Unicom_Wcdma implements ElementInfoService {

    @Autowired
    private CacheService_Unicom_Wcdma cacheService;

    @Autowired
    private UnicomProtectWcdmaMapper protectMapper;

    @Autowired
    private UnicomFavoriteWcdmaMapper favoriteMapper;

    @Autowired
    private UnicomGroupWcdmaMapper groupMapper;

    @Autowired
    private UnicomGroupInfoIndexWcdmaMapper groupInfoIndexMapper;

    @Autowired
    private UnicomNodeInfoIndexWcdmaMapper nodeInfoIndexMapper;

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
        String time = cacheService.getUpdateTimeForQuotaData();
        if (time == null || time.length() == 0) {
            return null;
        } else {
            return groupMapper.getGroupList(time);
        }
    }

    @Override
    public List<JSONObject> getGroupInfoList() {
        return groupMapper.getGroupInfoList();
    }

    @Override
    public List<String> getGroupNameList() {
        return groupMapper.getGroupNameList();
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
        return groupMapper.getGroupInfo(groupName);
    }

//    @Override
//    public ArrayList<String[]> getGroupInfoProcessed(String groupName) {
//
//        JSONObject infos = groupMapper.getGroupInfo(groupName);
//        List<JSONObject> infoIndexs = groupInfoIndexMapper.getGroupInfoIndex();
//
//        if (infos != null && infoIndexs != null){
//            ArrayList<String[]> result = new ArrayList<>();
//
//            for(JSONObject index : infoIndexs){
//
//                String key = index.getString("remark");
//                String value = infos.getString(index.getString("name"));
//
//                if (key != null && key.trim().length() > 0) {
//
//                    if (value != null && value.trim().length() > 0) {
//                        result.add(new String[]{key,value});
//
//                    } else {
//                        result.add(new String[]{key,Constants.ELEMENT_INFO_INVALID});
//                    }
//                }
//            }
//            return result;
//        }else{
//            return null;
//        }
//    }

    @Override
    public ArrayList<String[]> getGroupInfoProcessed(String groupName) {
        ArrayList<String[]> result = new ArrayList<>();
        JSONObject infos = groupMapper.getGroupInfo(groupName);

        for(String key : infos.keySet()){

            String value = infos.getString(key);
            if (value == null || value.trim().length() <= 0){
                value = Constants.ELEMENT_INFO_INVALID;
            }

            result.add(new String[]{key, value});

//            switch (key){
//                case "groupName" :
//                    result.add(new String[]{"网元名称", value});
//                    break;
//
//                case "groupId" :
//                    result.add(new String[]{"RNC ID", value});
//                    break;
//
//                case "msc" :
//                    result.add(new String[]{"归属MSC", value});
//                    break;
//
//                case "mscPool" :
//                    result.add(new String[]{"归属MSC POOL", value});
//                    break;
//
//                case "oss" :
//                    result.add(new String[]{"归属OSS", value});
//                    break;
//
//                case "pool" :
//                    result.add(new String[]{"归属SGSN POOL", value});
//                    break;
//
//                case "signalCode" :
//                    result.add(new String[]{"信令点编码", value});
//                    break;

//                        lac	varchar	10	可为空			LAC:40985
//                        rac	varchar	10	可为空			RAC：101
//                        a2ea_address	varchar	20	可为空			A2EA地址:10020019190000
//                        ip	varchar	20	可为空			RNC_IP地址：172.24.162.2
//                        swversion	varchar	50	可为空			当前软件版本：MAIN (C14.2-EP15-1)
//                        hwversion	varchar	50	可为空			硬件版本：RNC3820
//                        type	varchar	2	可为空			Type：C
//                        room	varchar	20	可为空			局址：北土城501、潘家园四层
//                        scope	varchar	255	可为空			覆盖范围：大兴、通州

//            }
        }
        return result;
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
        return nodeMapper.getNodeLocation(nodeName);
    }

    @Override
    public List<JSONObject> getNodeLocationsByGroup(String groupName) {
        return nodeMapper.getNodeLocationsByGroup(groupName);
    }

    @Override
    public List<String> getOssNameList() {
        return groupMapper.getOssNameList();
    }

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
        return protectMapper.getProtectListnodeName();
    }

    @Override
    public Integer updateStationName(JSONObject jsonObject) {
        return nodeMapper.updateStationName(jsonObject);
    }

    @Override
    public Integer addRnc(JSONObject jsonObject) {
        return groupMapper.addRnc(jsonObject);
    }

    @Override
    public Integer deleteGroup() {
        return groupMapper.deleteGroup();
    }

    @Override
    public Integer getGroupCounter() {
        return groupMapper.getGroupCounter();
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


}
