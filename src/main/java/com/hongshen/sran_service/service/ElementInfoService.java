package com.hongshen.sran_service.service;
import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.entity.UnicomNodeWcdma;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Created by poplar on 11/13/17.
 */
@Service
public interface ElementInfoService {


    List<JSONObject> getProtectList();

    List<JSONObject> getFavoriteList();

    int cancelCollection(String name);

    int addCollection(String name);

    List<JSONObject> getGroupList();

    List<JSONObject> getNodeListByGroup(String groupName);

    List<String> getNodeNameListByGroup(String groupName);

    List<JSONObject> getCellListByNode(String nodeName);

    List<String> getCellNameListByNode(String nodeName);

    JSONObject getGroupInfo(String groupName);

    JSONObject getNodeInfo(String nodeName);

    JSONObject getCellInfo(String cellName);

    JSONObject getNodelatitudeAndlongitude(String nodeName);

}
