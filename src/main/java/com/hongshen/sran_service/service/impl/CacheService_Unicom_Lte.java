package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.UnicomCounterLteMapper;
import com.hongshen.sran_service.dao.UnicomFormulaLteMapper;
import com.hongshen.sran_service.entity.UnicomFormulaLte;
import com.hongshen.sran_service.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<JSONObject> getCounterList() {

        List<JSONObject> result = counterMapper.getCounterList();
        for (JSONObject counter : counterList) {
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
                boolean quotaStatus = quota.getBoolean("quotaStatus");
                if (quotaStatus){
                    result.add(quota);
                }
            }
            return result;
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
