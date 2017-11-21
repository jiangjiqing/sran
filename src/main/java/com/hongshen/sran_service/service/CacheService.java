package com.hongshen.sran_service.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by poplar on 11/13/17.
 */
public interface CacheService {

    void resetCounterList();

    List<JSONObject> getCounterList();

    void resetFormulaList();

    List<JSONObject> getFormulaList(Boolean isVisible);


//    Map<String,Object> getCounterMap();
//    Map<String,Object> resetCounterMap();
//    Map<String,Object> getQuotaMap();
//    Map<String,Object> restQuotaMap();
//    Map<String,Object> getGroupThresholdMap();
//    Map<String,Object> restGroupThresholdMap();
}
