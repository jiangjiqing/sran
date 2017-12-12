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

    private String updateTimeForQuotaData;

    @Autowired
    private UnicomCounterWcdmaMapper counterMapper;


    private static List<JSONObject> counterList = new ArrayList<JSONObject>();


    private static List<JSONObject> counterListProcessed  = new ArrayList<JSONObject>();

    @Autowired
    private UnicomFormulaWcdmaMapper formulaMapper;


    private static List<JSONObject> formulaList = new ArrayList<JSONObject>();

    private static List<JSONObject> formulaListProcessed = new ArrayList<JSONObject>();

    @Autowired
    private UnicomQuotaThresholdGroupWcdmaMapper thresholdGroupMapper;


    private  static  List<JSONObject> thresholdGroupList = new ArrayList<>();

    @Autowired
    private UnicomQuotaThresholdNodeWcdmaMapper thresholdNodeMapper;


    private  static  List<JSONObject> thresholdNodeList = new ArrayList<>();

    @Autowired
    private UnicomQuotaThresholdCellWcdmaMapper thresholdCellMapper;


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
    public JSONObject getCounterByName(String name) {

        for (JSONObject counter : counterList){
            if (counter.getString("name").equals(name)){
                return counter;
            }
        }
        return null;
    }

    @Override
    public JSONObject getCounterProcessedByName(String name) {

        for (JSONObject counter : counterListProcessed){
            if (counter.getString("name").equals(name)){
                return counter;
            }
        }
        return null;
    }

    @Override
    public void resetFormulaList(){
        formulaList.clear();
        formulaList = formulaMapper.getFormulaList();
    }

    @Override
    public void resetFormulaListProcessed() {
        formulaListProcessed.clear();
        formulaListProcessed = formulaList;
    }

    @Override
    public List<JSONObject> getFormulaList(Boolean isVisible){

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
    public List<JSONObject> getFormulaListProcessed(Boolean isVisible){
        // no need to process
        return getFormulaList(isVisible);
    }

    @Override
    public JSONObject getFormulaByName(String quotaName) {

        for (JSONObject f : formulaList){
            if (f.getString("quotaName").equals(quotaName)){
                return f;
            }
        }
        return null;
    }

    @Override
    public JSONObject getFormulaProcessedByName(String quotaName) {

        for (JSONObject f : formulaListProcessed){
            if (f.getString("quotaName").equals(quotaName)){
                return f;
            }
        }
        return null;
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

    public String getUpdateTimeForQuotaData() {

        return updateTimeForQuotaData;
    }

    public void setUpdateTimeForQuotaData(String updateTimeForQuotaData) {

        this.updateTimeForQuotaData = updateTimeForQuotaData;
    }
}
