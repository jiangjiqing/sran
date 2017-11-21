package com.hongshen.sran_service.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
/**
 * Created by poplar on 11/20/17.
 */
public interface ElementInfoServiceQuotaService {
    List<JSONObject> getquota();

    List<JSONObject> getNodeByName(String groupName);

    List<JSONObject> getNodeList(String groupName);

    JSONObject getGroupByName(String groupName);
}
