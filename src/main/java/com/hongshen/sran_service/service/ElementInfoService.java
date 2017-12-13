package com.hongshen.sran_service.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.io.Reader;
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

    JSONObject getNodeInfo(String nodeName);

    JSONObject getCellInfo(String cellName);

    JSONObject getNodeLocation(String nodeName);

    JSONObject getTable(String gettableName);

    int deleteNode(String tableName, String name);

    List<JSONObject> getNodes(String tableName,String name);

    int deleteNodes(String tableName, String name);

    int addNode(String tableName,String name);

    int addNodes(String tableName, List<JSONObject> nodeNames);

    List<JSONObject> getNodeLocationsByGroup(String groupName);

    List<String> getOssNameList();

    Integer getNodeNum(String tableName, String groupName);

    Integer getNodeofNull(String tableName, String nodeName);
}
