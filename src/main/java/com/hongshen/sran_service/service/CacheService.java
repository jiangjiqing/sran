package com.hongshen.sran_service.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by poplar on 11/13/17.
 */
public interface CacheService {

    void resetCounterList();

    void resetCounterListProcessed();

    List<JSONObject> getCounterList(Boolean isValid);

    List<JSONObject> getCounterListProcessed(Boolean isValid);

//    List<String> getCounterNameList(Boolean isValid);
//
//    List<String> getCounterNameListProcessed(Boolean isValid);

    JSONObject getCounterByName(String name);

    JSONObject getCounterProcessedByName(String name);

    void resetFormulaList();

    void resetFormulaListProcessed();

    List<JSONObject> getFormulaList(Boolean isVisible);

    List<JSONObject> getFormulaListProcessed(Boolean isVisible);

    List<String> getFormulaNameList(Boolean isVisible);

    List<String> getFormulaNameListProcessed(Boolean isVisible);

    JSONObject getFormulaByName(String quotaName);

    JSONObject getFormulaProcessedByName(String quotaName);

    void  resetThresholdGroupList();

    List<JSONObject> getThresholdGroupList();

    void  resetThresholdNodeList();

    List<JSONObject> getThresholdNodeList();

    void  resetThresholdCellList();

    List<JSONObject> getThresholdCellList();

    String getUpdateTimeForQuotaData();

}
