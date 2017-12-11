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
public class CacheService_Unicom_Lte implements CacheService {

    private String updateTimeForQuotaData;

    @Autowired
    private UnicomCounterLteMapper counterMapper;


    private static List<JSONObject> counterList = new ArrayList<JSONObject>();


    private static List<JSONObject> counterListProcessed  = new ArrayList<JSONObject>();

    @Autowired
    private UnicomFormulaLteMapper formulaMapper;


    private static List<JSONObject> formulaList = new ArrayList<JSONObject>();

    private static List<JSONObject> formulaListProcessed  = new ArrayList<JSONObject>();

    @Autowired
    private UnicomQuotaThresholdGroupLteMapper thresholdGroupMapper;


    private  static  List<JSONObject> thresholdGroupList = new ArrayList<>();


    @Autowired
    private UnicomQuotaThresholdNodeLteMapper thresholdNodeMapper;


    private  static  List<JSONObject> thresholdNodeList = new ArrayList<>();

    @Autowired
    private UnicomQuotaThresholdCellLteMapper thresholdCellMapper;


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

        for (JSONObject counter : counterList){

            String name = counter.getString("name");

            if (name != null && name.length() != 0) {

                JSONObject temp = new JSONObject();

                String type = counter.getString("type");
                if ((name != null || name.length() != 0) &&
                        (type.equals("EUtranCellFDD") || type.equals("EUtranCellTDD"))) {
                    name = type + "_" + name;
                }

                temp.put("id", counter.getString("id"));
                temp.put("name", name);
                temp.put("type", type);
                temp.put("status", counter.getString("status"));

                counterListProcessed.add(temp);
            }
        }
    }

    @Override
    public List<JSONObject> getCounterList(Boolean isValid) {

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
    public List<JSONObject> getCounterListProcessed(Boolean isValid) {

        if (counterListProcessed.isEmpty()){
            resetCounterList();
        }

        if(isValid){

            List<JSONObject> resultList = new ArrayList<JSONObject>();

            for(JSONObject counter : counterListProcessed) {

                if (counter.getBoolean("status")){
                    resultList.add(counter);

                }else{
                    continue;
                }
            }
            return resultList;

        }else {
            return counterListProcessed;
        }
    }

    @Override
    public void resetFormulaList(){

        formulaList.clear();
        formulaList = formulaMapper.getFormulaList();

        resetFormulaListProcessed();
    }

    @Override
    public void resetFormulaListProcessed() {

        formulaListProcessed.clear();

        for (JSONObject f : formulaList){

            String quotaName = f.getString("quotaName");

            if (quotaName != null && quotaName.length() != 0){

                JSONObject temp = new JSONObject();

                String expression = f.getString("expression");
                if (expression != null || expression.length() != 0) {
                    expression = expression.replace(".", "_");
                }

                temp.put("id" , f.getString("id"));
                temp.put("quotaName" , quotaName);
                temp.put("expression" , expression);
                temp.put("remark" , f.getString("remark"));
                temp.put("status" , f.getString("status"));
                temp.put("type" , f.getString("type"));
                temp.put("hasTop10" , f.getString("hasTop10"));

                formulaListProcessed.add(temp);

            }
        }
    }

    @Override
    public List<JSONObject> getFormulaList(Boolean isVisible){

        if (formulaList.isEmpty()){
            resetFormulaList();
        }

        if (isVisible) {

            List<JSONObject> resultList = new ArrayList<JSONObject>();

            for (JSONObject f : formulaList) {

                // unvisible quota
                if (f.getBoolean("status")) {
                    resultList.add(f);

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
    public List<JSONObject> getFormulaListProcessed(Boolean isVisible) {

        if (formulaListProcessed.isEmpty()){
            resetFormulaList();
        }

        if (isVisible) {

            List<JSONObject> resultList = new ArrayList<JSONObject>();

            for (JSONObject f : formulaListProcessed) {

                // unvisible quota
                if (f.getBoolean("status")) {
                    resultList.add(f);

                } else {
                    continue;
                }
            }
            return resultList;

        }else{

            return formulaListProcessed;
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

    public String getUpdateTimeForQuotaData() {

        return updateTimeForQuotaData;
    }

    public void setUpdateTimeForQuotaData(String updateTimeForQuotaData) {

        this.updateTimeForQuotaData = updateTimeForQuotaData;
    }
}
