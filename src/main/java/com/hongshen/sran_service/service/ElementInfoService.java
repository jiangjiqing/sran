package com.hongshen.sran_service.service;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.entity.UnicomNodeWcdma;

import java.util.List;

/**
 * Created by poplar on 11/13/17.
 */
public interface ElementInfoService {

    List<JSONObject> getProtectList();

    List<JSONObject> getFavoriteList();

    int cancelCollection(String name);

    int addCollection(String name);

    List<JSONObject> getSpecifiedGroupList(String groupName);

    List<JSONObject> getSpecifiedNodeList(String groupName, String nodeName);


//   List<String> getProtect();
//
//    List<JSONObject> getGroupInfo(String name);
}
