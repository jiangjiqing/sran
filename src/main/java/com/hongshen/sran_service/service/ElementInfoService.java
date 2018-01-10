package com.hongshen.sran_service.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by poplar on 11/13/17.
 */
@Service
public interface ElementInfoService {


    List<JSONObject> getProtectList();

    List<JSONObject> getFavoriteList(String TableName);

    int cancelCollection(String name);

    int addCollection(String name);

    List<JSONObject> getGroupList(); // key = name

    List<JSONObject> getGroupInfoList(); // key = groupName

    List<String> getGroupNameList();

    List<JSONObject> getNodeInfoList();

    List<String> getNodeNameList();

    List<JSONObject> getCellInfoList();

    List<String> getCellNameList();

    List<JSONObject> getNodeListByGroup(String groupName);

    List<String> getNodeNameListByGroup(String groupName);

    List<JSONObject> getCellListByNode(String nodeName);

    List<String> getCellNameListByNode(String nodeName);

    JSONObject getGroupInfo(String groupName);

    ArrayList<String[]> getGroupInfoProcessed(String groupName);

    JSONObject getNodeInfo(String nodeName);

    ArrayList<String[]> getNodeInfoProcessed(String nodeName);

    JSONObject getCellInfo(String cellName);

//    ArrayList<String[]> getCellInfoProcessed(String cellName);

    JSONObject getNodeLocation(String nodeName);

    List<JSONObject> getNodeLocationsByGroup(String groupName);

    List<String> getOssNameList();

    Integer getNodeNum(String tableName, String groupName);

    Integer getNodeofNull(String tableName, String nodeName);

    Integer addProdectNode(String nodeName);

    Integer clearNodes();

    List<JSONObject> getProtectListnodeName();

    Integer updateStationName(JSONObject jsonObject);

    Integer addRnc(JSONObject jsonObject);

    Integer deleteGroup();

    Integer getGroupCounter();

    int createTable(String tableName);

    int addFavoriteFile(String tableName, List paramList);

    JSONObject getTable(String gettableName);

    List<JSONObject> getFavoriteNodes(String tableName,String name);

    int deleteFavoriteNode(String tableName, String name);

    int deleteFavoriteNodes(String tableName, String name);

    int addFavoriteNode(String tableName,String name);

    int addFavoriteNodes(String tableName, List<JSONObject> nodeNames);

    List<JSONObject> getProtectInfo();

    List<JSONObject> getNodeCountByname(String name);

    List<JSONObject> getNodeCountByNodeName(String name);

    JSONObject getGroupIdByName(String name);

    List<String> getVipNodeNameListByGroup(String groupName);
}
