package com.hongshen.sran_service.service;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.entity.UnicomQuotaThresholdCellLte;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by poplar on 11/13/17.
 */
public interface QuotaService {

    JSONObject getGroupQuotaByName(String groupName);

    JSONObject getNodeQuotaByName(String nodeName);

    JSONObject getCellQuotaByName(String cellName);

    int getGroupLevelByName(String groupName);

    JSONObject getNodeLevelByName(String nodeName);

    int getCellLevelByName(String cellName);

    JSONObject getNodeLevel(String node_name);

    JSONObject getCellLevel(String cellName);

    Integer setGroup(JSONObject quotaThres);

    Integer setNode(JSONObject quotaThres);

    Integer setCell(JSONObject quotaThres);

    List<JSONObject>  getQuotas(Date start, Date end);
}
