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
    public List<JSONObject> getCounterList(Boolean isValid){

        // check cache
        if (counterList.isEmpty()){
            resetCounterList();
        }

        if(isValid){

            List<JSONObject> resultList = new ArrayList<JSONObject>();

            for(JSONObject counter : counterList) {

                if (!counter.getBoolean("status")){
                    continue;

                }else{
                    resultList.add(counter);
                }
            }
            return resultList;

        }else {
            return counterList;
        }
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

        // check cache
        if (formulaList.isEmpty()){
            resetFormulaList();
        }

        if (isVisible) {

            List<JSONObject> resultList = new ArrayList<JSONObject>();

            for (JSONObject formula : formulaList) {

                // unvisible quota
                if (!formula.getBoolean("status")) {
                    continue;

                } else {
                    resultList.add(formula);
                }
            }
            return resultList;

        }else{
            return formulaList;
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
