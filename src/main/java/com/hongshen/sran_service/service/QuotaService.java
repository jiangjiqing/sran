package com.hongshen.sran_service.service;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

    Integer setGroupThreshold(JSONObject params);

    Integer setNodeThreshold(JSONObject params);

    Integer setCellThreshold(JSONObject params);

    List<JSONObject> getQuotas(Date start, Date end, String condition);

    List<JSONObject> getQuotasNode(Date start, Date end, String condition);

    List<JSONObject> getQuotasCell(Date start, Date end, String condition);

    List<JSONObject> getCounterExportGroup(String counters, Date start, Date end, String condition);

    List<JSONObject> getGroupQuotaBadTenCell(String groupName, String quotaName);

    List<JSONObject> getFormula(Boolean isVisible);

    Integer addFormula(JSONObject formulaJson);

    Integer updateFormula(JSONObject formula);

    Integer DeleteFormula(String quotaName);

    Integer deleteAllFormulas();

    List<JSONObject> getGroupTime();

    List<JSONObject> getNodeTime();

    List<JSONObject> getCellTime();

    List<JSONObject> getCounterTime();

    void addColumnGroup(String formula);
    void addColumnNode(String formula);
    void addColumnCell(String formula);

    Integer addCounter(JSONObject jsonObject);

    void addColumnCounter(String name,String nullable,String type);

    List<JSONObject> getCounterList();

    List<JSONObject> getColumns();

    void deleteColumnCounter(String colum);

    int deleteCounters();
}
