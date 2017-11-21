package com.hongshen.sran_service.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.UnicomCounterWcdmaMapper;
import com.hongshen.sran_service.dao.UnicomFormulaWcdmaMapper;
import com.hongshen.sran_service.entity.UnicomFormulaWcdma;
import com.hongshen.sran_service.service.CacheService;
import jdk.nashorn.internal.objects.annotations.Constructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by poplar on 11/13/17.
 */
@Service
public class CacheService_Unicom_Wcdma implements CacheService {

    @Autowired
    private UnicomCounterWcdmaMapper counterMapper;

    @Autowired
    private static List<JSONObject> counterList = new ArrayList<JSONObject>();

    @Autowired
    private UnicomFormulaWcdmaMapper formulaMapper;

    @Autowired
    private static List<JSONObject> formulaList = new ArrayList<JSONObject>();


    @Override
    public void resetCounterList(){

        // clear data
        counterList.clear();

        // set data
        counterList = counterMapper.getCounterList();
    }

    @Override
    public List<JSONObject> getCounterList(){

        List<JSONObject> result = counterMapper.getCounterList();
        for(JSONObject counter : counterList) {
            String counterName = counter.getString("name");
//            boolean counterStatus = counter.getBoolean("status");
            boolean counterStatus = false;//TODO
            if (counterStatus) {
                result.add(counter);
            }
        }
        return result;
    }

    @Override
    public void resetFormulaList(){

        // clear data
        formulaList.clear();

        // set data
        formulaList = formulaMapper.getFormulaList();
    }

    @Override
    public List<JSONObject> getFormulaList(Boolean isVisible){

        if (!isVisible){
            return formulaList;

        }else{
            List<JSONObject> result = formulaMapper.getFormulaList();
            for(JSONObject quota : formulaList){
                String quotaName = quota.getString("quotaName");
                boolean quotaStatus = quota.getBoolean("status");
                if (quotaStatus){
                    result.add(quota);
                }
            }
            return result;
        }
    }





//    @Override
//    public Map<String, Object> getCounterMap() {
//        return unicomCounterWcdmaMapper.getCounterMap();
//    }
//
//    @Override
//    public Map<String, Object> resetCounterMap() {
//        return unicomCounterWcdmaMapper.resetCounterMap();
//    }
//
//    @Override
//    public Map<String, Object> getQuotaMap() {
//        return unicomCounterWcdmaMapper.getQuotaMap();
//    }
//
//    @Override
//    public Map<String, Object> restQuotaMap() {
//        return unicomCounterWcdmaMapper.restQuotaMap();
//    }
//
//    @Override
//    public Map<String, Object> getGroupThresholdMap() {
//        return unicomCounterWcdmaMapper.getGroupThresholdMap();
//    }
//
//    @Override
//    public Map<String, Object> restGroupThresholdMap() {
//        return unicomCounterWcdmaMapper.restGroupThresholdMap();
//    }
}
