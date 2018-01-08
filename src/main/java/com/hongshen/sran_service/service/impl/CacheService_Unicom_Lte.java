package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.*;
import com.hongshen.sran_service.service.CacheService;
import com.hongshen.sran_service.service.util.Constants;
import com.hongshen.sran_service.service.util.DataHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by poplar on 11/13/17.
 */
@Service
public class CacheService_Unicom_Lte implements CacheService {

    private String updateTimeForQuotaData;

    @Autowired
    private UnicomCounterLteMapper counterMapper;

    @Autowired
    private UnicomQuotaHistoryGroupLteMapper quotaGroupMapper;

    @Autowired
    private UnicomQuotaHistoryNodeLteMapper quotaNodeMapper;

    @Autowired
    private UnicomQuotaHistoryCellLteMapper quotaCellMapper;


    private List<JSONObject> counterList = new ArrayList<JSONObject>();


    private List<JSONObject> counterListProcessed  = new ArrayList<JSONObject>();

    @Autowired
    private UnicomFormulaLteMapper formulaMapper;


    private List<JSONObject> formulaList = new ArrayList<JSONObject>();

    private List<JSONObject> formulaListProcessed  = new ArrayList<JSONObject>();

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
                        (type != null || type.length() != 0)) {
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

        if (counterList == null || counterList.isEmpty()){
            resetCounterList();
        }

        List<JSONObject> resultList = new ArrayList<JSONObject>();

        for(JSONObject counter : counterList) {

            if (isValid && counter.getBoolean("status")){
                continue;

            }else{
                resultList.add(counter);
            }
        }
        return resultList;
    }

    @Override
    public List<JSONObject> getCounterListProcessed(Boolean isValid) {

        List<JSONObject> resultList = new ArrayList<JSONObject>();

        if (counterListProcessed == null || counterListProcessed.isEmpty()){
            resetCounterList();
        }

        for(JSONObject counter : counterListProcessed) {

            if (isValid && !counter.getBoolean("status")){
                continue;

            }else{
                resultList.add(counter);
            }
        }
        return resultList;
    }

    @Override
    public List<String> getCounterNameList(Boolean isValid) {

        if (counterList == null || counterList.isEmpty()){
            resetCounterList();
        }

        List<String> resultList = new ArrayList<>();

        for (JSONObject counter : counterList) {

            // Valid counter
            if (isValid && !counter.getBoolean("status")) {
                continue;

            } else {
                resultList.add(counter.getString("name"));
            }
        }
        return resultList;
    }

    @Override
    public List<String> getCounterNameListProcessed(Boolean isValid) {

        if (counterListProcessed == null || counterListProcessed.isEmpty()){
            resetCounterList();
        }

        List<String> resultList = new ArrayList<>();

        for (JSONObject counter : counterListProcessed) {

            // Valid counter
            if (isValid && !counter.getBoolean("status")) {
                continue;

            } else {
                resultList.add(counter.getString("type") + "_" + counter.getString("name"));
            }
        }
        return resultList;
    }

    @Override
    public JSONObject getCounterByName(String name) {

        if (counterList == null || counterList.isEmpty()){
            resetCounterList();
        }

        for (JSONObject counter : counterList){
            if (counter.getString("name").equals(name)){
                return counter;
            }
        }
        return null;
    }

    @Override
    public JSONObject getCounterProcessedByName(String name) {

        if (counterListProcessed == null || counterListProcessed.isEmpty()){
            resetCounterList();
        }

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

        resetFormulaListProcessed();
        resetThresholdCellList();
        resetThresholdNodeList();
        resetThresholdGroupList();
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

                temp.put("quotaName" , quotaName);
                temp.put("expression" , expression);
                temp.put("remark" , f.getString("remark"));
                temp.put("status" , f.getString("status"));
                temp.put("unit" , f.getString("unit"));
                temp.put("hasTop10" , f.getString("hasTop10"));

                formulaListProcessed.add(temp);

            }
        }
    }

    @Override
    public List<JSONObject> getFormulaList(Boolean isVisible){

        List<JSONObject> resultList = new ArrayList<JSONObject>();

        if (formulaList == null || formulaList.isEmpty()){
            resetFormulaList();
        }

        for (JSONObject f : formulaList) {

            // unvisible quota
            if (isVisible && !f.getBoolean("status")) {
                continue;

            } else {
                resultList.add(f);
            }
        }
        return resultList;
    }

    @Override
    public List<JSONObject> getFormulaListProcessed(Boolean isVisible) {

        List<JSONObject> resultList = new ArrayList<JSONObject>();

        if (formulaListProcessed == null || formulaListProcessed.isEmpty()){
            resetFormulaList();
        }

        for (JSONObject f : formulaListProcessed) {

            // unvisible quota
            if (isVisible && !f.getBoolean("status")) {
                continue;

            } else {
                resultList.add(f);
            }
        }
        return resultList;
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

        if (formulaListProcessed == null || formulaListProcessed.isEmpty()){
            resetFormulaList();
        }

        List<String> resultList = new ArrayList<>();

        for (JSONObject formula : formulaListProcessed) {

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
        thresholdGroupList = thresholdGroupMapper.getThresholdList();
    }

    @Override
    public List<JSONObject> getThresholdGroupList() {

        if (thresholdGroupList == null || thresholdGroupList.isEmpty()){
            resetThresholdGroupList();
        }
        return thresholdGroupList;
    }

    @Override
    public Map<String, JSONObject> getThresholdGroupMap() {

        if (thresholdGroupList == null || thresholdGroupList.isEmpty()){
            resetThresholdGroupList();
        }
        return DataHelper.JsonListToJsonMap(thresholdGroupList,"quotaName");
    }

    @Override
    public void resetThresholdNodeList() {

        thresholdNodeList.clear();
        thresholdNodeList = thresholdNodeMapper.getThresholdList();
    }

    @Override
    public List<JSONObject> getThresholdNodeList() {

        if (thresholdNodeList == null || thresholdNodeList.isEmpty()){
            resetThresholdNodeList();
        }
        return thresholdNodeList;
    }

    @Override
    public Map<String, JSONObject> getThresholdNodeMap() {

        if (thresholdNodeList == null || thresholdNodeList.isEmpty()){
            resetThresholdNodeList();
        }
        return DataHelper.JsonListToJsonMap(thresholdNodeList,"quotaName");
    }

    @Override
    public void resetThresholdCellList() {

        thresholdCellList.clear();
        thresholdCellList = thresholdCellMapper.getThresholdList();
    }

    @Override
    public List<JSONObject> getThresholdCellList() {

        if (thresholdCellList == null || thresholdCellList.isEmpty()){
            resetThresholdCellList();
        }
        return thresholdCellList;
    }

    @Override
    public Map<String, JSONObject> getThresholdCellMap() {

        if (thresholdCellList == null || thresholdCellList.isEmpty()){
            resetThresholdCellList();
        }
        return DataHelper.JsonListToJsonMap(thresholdCellList,"quotaName");
    }

    public String getUpdateTimeForQuotaData(){

        String time = this.updateTimeForQuotaData;

        if (time == null || time.length() == 0) {

            Date date = getUpdateTimeForQuotaData(Constants.LEVEL_CELL);

            if (date == null) {
                date = getUpdateTimeForQuotaData(Constants.LEVEL_NODE);
            }

            if (date == null) {
                date = getUpdateTimeForQuotaData(Constants.LEVEL_GROUP);
            }

            if (date != null) {
                time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
            }
        }
        return time;
    }

    public Date getUpdateTimeForQuotaData(String level) {

        if (this.updateTimeForQuotaData == null || this.updateTimeForQuotaData.length() == 0){

            switch (level){
                case Constants.LEVEL_GROUP:
                    return quotaGroupMapper.getQuotaLastUpdateTime();

                case Constants.LEVEL_NODE:
                    return quotaNodeMapper.getQuotaLastUpdateTime();

                case Constants.LEVEL_CELL:
                    return quotaCellMapper.getQuotaLastUpdateTime();

                default:
                    return null;
            }
        }else{
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟
                return sdf.parse(this.updateTimeForQuotaData);

            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public void setUpdateTimeForQuotaData(String updateTimeForQuotaData) {
        this.updateTimeForQuotaData = updateTimeForQuotaData;
    }
}
