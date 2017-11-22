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

    List<JSONObject> getNodeList(String groupName);

    JSONObject getGroupByName(String groupName);//TODO

    List<JSONObject> getSpecifiedGroupList(String groupName);

    List<JSONObject> getSpecifiedNodeList(String groupName, String nodeName);


//   List<String> getProtect();
//
//    List<JSONObject> getGroupInfo(String name);
}
