package com.hongshen.sran_service.service;

import com.alibaba.fastjson.JSONObject;

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

    int getNodeLevelByName(String nodeName);

    int getCellLevelByName(String cellName);

    JSONObject getNodeLevel(String node_name);
}
