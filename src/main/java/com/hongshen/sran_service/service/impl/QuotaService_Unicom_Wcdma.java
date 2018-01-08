package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.*;
import com.hongshen.sran_service.service.QuotaService;
import com.hongshen.sran_service.service.util.ScannerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by poplar on 11/13/17.
 */
@Service
public class QuotaService_Unicom_Wcdma implements QuotaService {

    @Autowired
    private CacheService_Unicom_Wcdma cacheService;

    @Autowired
    private UnicomFormulaWcdmaMapper formulaMapper;

    @Autowired
    private UnicomCounterWcdmaMapper counterMapper;

    @Autowired
    private UnicomCounterHistoryWcdmaMapper counterHistoryWcdmaMapper;

    @Autowired
    private UnicomQuotaHistoryGroupWcdmaMapper quotaHistoryGroupMapper;

    @Autowired
    private UnicomQuotaHistoryNodeWcdmaMapper quotaHistoryNodeMapper;

    @Autowired
    private UnicomQuotaHistoryCellWcdmaMapper quotaHistoryCellMapper;

    @Autowired
    private UnicomQuotaThresholdGroupWcdmaMapper quotaThresholdGroupMapper;

    @Autowired
    private UnicomQuotaThresholdNodeWcdmaMapper quotaThresholdNodeMapper;

    @Autowired
    private UnicomQuotaThresholdCellWcdmaMapper quotaThresholdCellMapper;

    @Autowired
    private UnicomGroupWcdmaMapper groupMapper;

    @Autowired
    private UnicomNodeWcdmaMapper nodeMapper;

    @Autowired
    private UnicomCellWcdmaMapper cellMapper;

    @Autowired
    private UnicomCounterHistoryWcdmaMapper counterHistoryMapper;

    @Override
    public JSONObject getGroupQuota(String groupName) {
        String time = cacheService.getUpdateTimeForQuotaData();
        if (time == null || time.length() == 0){
            return null;
        }else {
            return quotaHistoryGroupMapper.getQuota(groupName, time);
        }
    }

    @Override
    public JSONObject getNodeQuota(String nodeName){
        String time = cacheService.getUpdateTimeForQuotaData();
        if (time == null || time.length() == 0){
            return null;
        }else {
            return quotaHistoryNodeMapper.getQuota(nodeName, time);
        }
    }

    @Override
    public JSONObject getCellQuota(String cellName){
        String time = cacheService.getUpdateTimeForQuotaData();
        if (time == null || time.length() == 0){
            return null;
        }else {
            return quotaHistoryCellMapper.getQuota(cellName, time);
        }
    }

    @Override
    public JSONObject getGroupLevel(String groupName) {
        String time = cacheService.getUpdateTimeForQuotaData();
        if (time == null || time.length() == 0){
            return null;
        }else {
            return quotaHistoryGroupMapper.getLevel(groupName, time);
        }
    }

    @Override
    public JSONObject getNodeLevel(String nodeName) {
        String time = cacheService.getUpdateTimeForQuotaData();
        if (time == null || time.length() == 0){
            return null;
        }else {
            return quotaHistoryNodeMapper.getLevel(nodeName, time);
        }
    }

    @Override
    public JSONObject getCellLevel(String cellName) {
        String time = cacheService.getUpdateTimeForQuotaData();
        if (time == null || time.length() == 0){
            return null;
        }else {
            return quotaHistoryCellMapper.getLevel(cellName, time);
        }
    }

    @Override
    public Integer setGroupThreshold(JSONObject params) {
        return quotaThresholdGroupMapper.setThreshold(params);
    }

    @Override
    public Integer setNodeThreshold(JSONObject params) {

        return quotaThresholdNodeMapper.setThreshold(params);
    }

    @Override
    public Integer setCellThreshold(JSONObject params) {

        return quotaThresholdCellMapper.setThreshold(params);
    }

    @Override
    public List<JSONObject>  getQuotas(Date start, Date end,String condition) {
        return quotaHistoryGroupMapper.getQuotas(start,end,condition);
    }

    @Override
    public List<JSONObject> getQuotasNode(Date start, Date end, String condition) {
        return quotaHistoryNodeMapper.getQuotasNode(start,end,condition);
    }

    @Override
    public List<JSONObject> getQuotasCell(Date start, Date end, String condition) {
        return quotaHistoryCellMapper.getQuotasCell(start,end,condition);
    }

    @Override
    public List<JSONObject> getCounterExportGroup(String counters, Date start, Date end, String condition) {
        return counterHistoryWcdmaMapper.dowloadCounter(counters,start,end,condition);
    }

    @Override
    public List<JSONObject> getGroupQuotaBadTenCell(String groupName, String quotaName) {

        List<JSONObject> resultList = new ArrayList<>();

        JSONObject formula = cacheService.getFormulaProcessedByName(quotaName);

        if (formula == null) {

            return resultList;
        }

        String expression = formula.getString("expression");

        List<String> nodeNameList = nodeMapper.getNodeNameListByGroup(groupName);

        if (nodeNameList.size() == 0) {

            return resultList;
        }

        List<String> cellList = cellMapper.getCellNameListByNodeNameList(nodeNameList);

        if (cellList.size() == 0) {

            return resultList;
        }

        resultList = counterHistoryMapper.getBadTenCell(expression, cellList, cacheService.getUpdateTimeForQuotaData());

        return resultList;
    }

    @Override
    public List<JSONObject> getFormula(Boolean isVisible) {
        return cacheService.getFormulaList(isVisible);
    }

    @Override
    public Integer addFormula(JSONObject formula) {
        return formulaMapper.addFormula(formula);
    }

    @Override
    public Integer updateFormula(JSONObject formula) {
        return formulaMapper.updateFormula(formula);
    }

    @Override
    public Integer DeleteFormula(String quotaName) {
        return formulaMapper.deleteFormulaByName(quotaName);
    }

    @Override
    public Integer deleteAllFormulas() {
        return formulaMapper.deleteAllFormulas();
    }

    @Override
    public List<JSONObject> getGroupTime() {
        return quotaHistoryGroupMapper.getGroupTime();
    }

    @Override
    public List<JSONObject> getNodeTime() {
        return quotaHistoryNodeMapper.getNodeTime();
    }

    @Override
    public List<JSONObject> getCellTime() {
        return quotaHistoryCellMapper.getCellTime();
    }

    @Override
    public List<JSONObject> getCounterTime() {
        return counterHistoryWcdmaMapper.getCounterTime();
    }

    @Override
    public void addColumnGroup(String formula) {

         quotaHistoryGroupMapper.addColumnGroup(formula);
    }

    @Override
    public void addColumnNode(String formula) {

        quotaHistoryNodeMapper.addColumnNode(formula);
    }

    @Override
    public void addColumnCell(String formula) {

         quotaHistoryCellMapper.addColumnCell(formula);
    }

    @Override
    public Integer addCounter(JSONObject jsonObject) {
        return counterMapper.addCounter(jsonObject);
    }

    @Override
    public void addColumnCounter(String name,String nullable,String type) {
        counterHistoryWcdmaMapper.addColumnCounter(name,nullable,type);
    }

    @Override
    public List<JSONObject> getCounterList() {
        return counterMapper.getCounterList();
    }
    @Override
    public List<JSONObject> getColumns() {
        return counterHistoryWcdmaMapper.getColumns();
    }

    @Override
    public void deleteColumnCounter(String colum) {
        counterHistoryWcdmaMapper.deleteColumnCounter(colum);
    }

    @Override
    public int deleteCounters() {
        return counterMapper.deleteCounters();
    }

}
