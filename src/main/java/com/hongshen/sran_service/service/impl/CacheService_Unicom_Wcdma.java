package com.hongshen.sran_service.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.*;
import com.hongshen.sran_service.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by poplar on 11/13/17.
 */
@Service
public class CacheService_Unicom_Wcdma implements CacheService {

    @Autowired
    private static String updateTimeForQuotaData = "";

    @Autowired
    private UnicomCounterWcdmaMapper counterMapper;

    @Autowired
    private static List<JSONObject> counterList = new ArrayList<JSONObject>();

    @Autowired
    private static List<JSONObject> counterListProcessed  = new ArrayList<JSONObject>();

    @Autowired
    private UnicomFormulaWcdmaMapper formulaMapper;

    @Autowired
    private static List<JSONObject> formulaList = new ArrayList<JSONObject>();

    @Autowired
    private UnicomQuotaThresholdGroupWcdmaMapper thresholdGroupMapper;

    @Autowired
    private  static  List<JSONObject> thresholdGroupList = new ArrayList<>();

    @Autowired
    private UnicomQuotaThresholdNodeWcdmaMapper thresholdNodeMapper;

    @Autowired
    private  static  List<JSONObject> thresholdNodeList = new ArrayList<>();

    @Autowired
    private UnicomQuotaThresholdCellWcdmaMapper thresholdCellMapper;

    @Autowired
    private  static  List<JSONObject> thresholdCellList = new ArrayList<>();

    @Override
    public void resetCounterList(){

        counterList.clear();

        counterList = counterMapper.getCounterList();

        resetCounterListProcessed();
    }

    @Override
    public void resetCounterListProcessed(){

        counterListProcessed.clear();

        counterListProcessed = counterList;
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

                if (counter.getBoolean("status")){
                    resultList.add(counter);

                }else{
                    continue;
                }
            }
            return resultList;

        }else {
            return counterList;
        }
    }

    @Override
    public List<JSONObject> getCounterListProcessed(Boolean isValid){
        // no need to process
        return getCounterList(isValid);
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
                if (formula.getBoolean("status")) {
                    resultList.add(formula);

                } else {
                    continue;
                }
            }
            return resultList;

        }else{
            return formulaList;
        }
    }

    @Override
    public void resetThresholdGroupList() {

        thresholdGroupList.clear();

        thresholdGroupList = thresholdGroupMapper.getThresholdGroupList();
    }

    @Override
    public List<JSONObject> getThresholdGroupList() {
        if (thresholdGroupList.isEmpty()){
            resetThresholdGroupList();
        }
        return thresholdGroupList;
    }

    @Override
    public void resetThresholdNodeList() {
        thresholdNodeList.clear();

        thresholdNodeList = thresholdNodeMapper.getThresholdNodeList();
    }

    @Override
    public List<JSONObject> getThresholdNodeList() {
        if (thresholdNodeList.isEmpty()){
            resetThresholdNodeList();
        }
        return thresholdNodeList;
    }

    @Override
    public void resetThresholdCellList() {
        thresholdCellList.clear();

        thresholdCellList = thresholdCellMapper.getThresholdCellList();
    }

    @Override
    public List<JSONObject> getThresholdCellList() {
        if (thresholdCellList.isEmpty()){
            resetThresholdCellList();
        }
        return thresholdCellList;
    }
}
