package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.*;
import com.hongshen.sran_service.service.QuotaService;
import com.hongshen.sran_service.service.util.Constants;
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
    public Integer setGroupThreshold(JSONObject quotaThres) {
        return quotaThresholdGroupMapper.setGroup(quotaThres);
    }

    @Override
    public Integer setNodeThreshold(JSONObject quotaThres) {

        return quotaThresholdNodeMapper.setNode(quotaThres);
    }

    @Override
    public Integer setCellThreshold(JSONObject quotaThres) {

        return quotaThresholdCellMapper.setCell(quotaThres);
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
    public List<JSONObject> getCounterExportGroup(Date start, Date end, String condition) {
        return counterHistoryWcdmaMapper.dowloadCounter(start,end,condition);
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
        return cacheService.getCounterList(isVisible);
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
}
