package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.UnicomCounterLteMapper;
import com.hongshen.sran_service.dao.UnicomFormulaLteMapper;
import com.hongshen.sran_service.entity.UnicomFormulaLte;
import com.hongshen.sran_service.service.CacheService;
import jdk.nashorn.internal.objects.annotations.Constructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.ConstructorProperties;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by poplar on 11/13/17.
 */
@Service
public class CacheService_Unicom_Lte implements CacheService {

    @Autowired
    private UnicomCounterLteMapper counterMapper;

    private static List<JSONObject> counterList = new ArrayList<JSONObject>();

    @Autowired
    private UnicomFormulaLteMapper formulaMapper;

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
    public List<JSONObject> getCounterList(Boolean isValid) {

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

            for (JSONObject f : formulaList) {

                // unvisible quota
                if (!f.getBoolean("status")) {
                    continue;

                } else {
                    resultList.add(f);
                }
            }
            return resultList;

        }else{
            return formulaList;
        }
    }

//    @Autowired
//    private UnicomCounterLteMapper unicomCounterLteMapper;
//    @Override
//    public Map<String, Object> getCounterMap() {
//        return unicomCounterLteMapper.getCounterMap();
//    }
//
//    @Override
//    public Map<String, Object> resetCounterMap() {
//        return unicomCounterLteMapper.resetCounterMap();
//    }
//
//    @Override
//    public Map<String, Object> getQuotaMap() {
//        return unicomCounterLteMapper.getQuotaMap();
//    }
//
//    @Override
//    public Map<String, Object> restQuotaMap() {
//        return unicomCounterLteMapper.restQuotaMap();
//    }
//
//    @Override
//    public Map<String, Object> getGroupThresholdMap() {
//        return unicomCounterLteMapper.getGroupThresholdMap();
//    }
//
//    @Override
//    public Map<String, Object> restGroupThresholdMap() {
//        return unicomCounterLteMapper.restGroupThresholdMap();
//    }
}
