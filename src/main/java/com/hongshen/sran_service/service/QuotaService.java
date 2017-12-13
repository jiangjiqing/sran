package com.hongshen.sran_service.service;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * Created by poplar on 11/13/17.
 */
public interface QuotaService {

    JSONObject getGroupQuota(String groupName);

    JSONObject getNodeQuota(String nodeName);

    JSONObject getCellQuota(String cellName);

    JSONObject getGroupLevel(String groupName);

    JSONObject getNodeLevel(String nodeName);

    JSONObject getCellLevel(String cellName);

    Integer setGroupThreshold(JSONObject quotaThres);

    Integer setNodeThreshold(JSONObject quotaThres);

    Integer setCellThreshold(JSONObject quotaThres);

    List<JSONObject> getQuotas(Date start, Date end, String condition);

    List<JSONObject> getQuotasNode(Date start, Date end, String condition);

    List<JSONObject> getQuotasCell(Date start, Date end, String condition);

    List<JSONObject> getCounterExportGroup(Date start,Date end,String condition);

    List<JSONObject> getGroupQuotaBadTenCell(String groupName, String quotaName);

    List<JSONObject> getFormula(Boolean isVisible);

    Integer addFormula(JSONObject formulaJson);

    Integer deleteFormulas();
}
