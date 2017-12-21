package com.hongshen.sran_service.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.*;
import com.hongshen.sran_service.service.CacheService;
import com.hongshen.sran_service.service.util.Constants;
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

    @Autowired
    private UnicomQuotaHistoryGroupWcdmaMapper quotaGroupMapper;

    @Autowired
    private UnicomQuotaHistoryNodeWcdmaMapper quotaNodeMapper;

    @Autowired
    private UnicomQuotaHistoryCellWcdmaMapper quotaCellMapper;


    private List<JSONObject> counterList = new ArrayList<JSONObject>();


    private List<JSONObject> counterListProcessed  = new ArrayList<JSONObject>();

    @Autowired
    private UnicomFormulaWcdmaMapper formulaMapper;


    private List<JSONObject> formulaList = new ArrayList<JSONObject>();

    private List<JSONObject> formulaListProcessed = new ArrayList<JSONObject>();

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

        List<JSONObject> resultList = new ArrayList<JSONObject>();

        if (counterList == null || counterList.isEmpty()){
            resetCounterList();
        }

        for(JSONObject counter : counterList) {

            if (isValid && !counter.getBoolean("status")){
                continue;

            }else{
                resultList.add(counter);
            }
        }
        return resultList;
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
                System.out.println(formulaList);
    }

    @Override
    public void resetFormulaListProcessed() {
        formulaListProcessed.clear();
        formulaListProcessed = formulaList;
    }

    @Override
    public List<JSONObject> getFormulaList(Boolean isVisible){

        List<JSONObject> resultList = new ArrayList<JSONObject>();

        if (formulaList == null || formulaList.isEmpty()){
            resetFormulaList();
        }

        for (JSONObject formula : formulaList) {

            // unvisible quota
            if (isVisible && !formula.getBoolean("status")) {
                continue;

            } else {
                resultList.add(formula);
            }
        }
        return resultList;
    }

    @Override
    public List<JSONObject> getFormulaListProcessed(Boolean isVisible){
        // no need to process
        return getFormulaList(isVisible);
    }

    @Override
    public List<String> getFormulaNameList(Boolean isVisible) {

        if (formulaList == null || formulaList.isEmpty()){
            resetFormulaList();
        }

        List<String> resultList = new ArrayList<>();

        for (JSONObject formula : formulaList) {

            // unvisible quota
            if (isVisible && !formula.getBoolean("status")) {
                continue;

            } else {
                resultList.add(formula.getString("quotaName"));
            }
        }
        return resultList;
    }

    @Override
    public List<String> getFormulaNameListProcessed(Boolean isVisible) {
        // no need to process
        return getFormulaNameList(isVisible);
    }

    @Override
    public JSONObject getFormulaByName(String quotaName) {
        if (formulaList == null || formulaList.isEmpty()){
            resetFormulaList();
        }
        for (JSONObject f : formulaList){
            if (f.getString("quotaName").equals(quotaName)){
                return f;
            }
        }
        return null;
    }

    @Override
    public JSONObject getFormulaProcessedByName(String quotaName) {
        if (formulaListProcessed == null || formulaListProcessed.isEmpty()){
            resetFormulaList();
        }
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

        if (thresholdGroupList == null || thresholdGroupList.isEmpty()){
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

        if (thresholdNodeList == null || thresholdNodeList.isEmpty()){
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

        if (thresholdCellList == null || thresholdCellList.isEmpty()){
            resetThresholdCellList();
        }
        return thresholdCellList;
    }

    public String getUpdateTimeForQuotaData(){

        String time = this.updateTimeForQuotaData;

        if (time == null || time.length() == 0){
            time = getUpdateTimeForQuotaData(Constants.LEVEL_GROUP);
        }

        if (time == null || time.length() == 0){
            time = getUpdateTimeForQuotaData(Constants.LEVEL_NODE);
        }

        if (time == null || time.length() == 0){
            time = getUpdateTimeForQuotaData(Constants.LEVEL_CELL);
        }

        return time;
    }

    public String getUpdateTimeForQuotaData(String level) {

        switch (level){
            case Constants.LEVEL_GROUP:
                return quotaGroupMapper.getQuotaLastUpdateTime().getString("time");

            case Constants.LEVEL_NODE:
                return quotaNodeMapper.getQuotaLastUpdateTime().getString("time");

            case Constants.LEVEL_CELL:
                return quotaCellMapper.getQuotaLastUpdateTime().getString("time");

            default:
                return "";
        }
    }

    public void setUpdateTimeForQuotaData(String updateTimeForQuotaData) {
        this.updateTimeForQuotaData = updateTimeForQuotaData;
    }
}
