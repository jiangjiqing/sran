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
public class QuotaService_Unicom_Lte implements QuotaService {

    @Autowired
    private CacheService_Unicom_Lte cacheService;

    @Autowired
    private UnicomFormulaLteMapper formulaMapper;

    @Autowired
    private UnicomCounterLteMapper counterMapper;

    @Autowired
    private UnicomGroupWcdmaMapper groupMapper;

    @Autowired
    private UnicomNodeWcdmaMapper nodeMapper;

    @Autowired
    private UnicomCellWcdmaMapper cellMapper;

    @Autowired
    private UnicomCounterHistoryLteMapper counterHistoryMapper;

    @Autowired
    private UnicomQuotaHistoryGroupLteMapper quotaHistoryGroupMapper;

    @Autowired
    private UnicomQuotaHistoryNodeLteMapper quotaHistoryNodeMapper;

    @Autowired
    private UnicomQuotaHistoryCellLteMapper quotaHistoryCellMapper;

    @Autowired
    private UnicomQuotaThresholdGroupLteMapper quotaThresholdGroupMapper;

    @Autowired
    private UnicomQuotaThresholdNodeLteMapper quotaThresholdNodeMapper;

    @Autowired
    private UnicomQuotaThresholdCellLteMapper quotaThresholdCellMapper;


    @Override
    public JSONObject getGroupQuota(String groupName) {
        String time = cacheService.getUpdateTimeForQuotaData();
        if (time == null || time.length() == 0){
            return null;
        }else{
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
        return counterHistoryMapper.dowloadCounter(counters,start,end,condition);
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
        return counterHistoryMapper.getCounterTime();
    }


    @Override
    public Integer addCounter(JSONObject jsonObject) {
        return counterMapper.addCounter(jsonObject);
    }

    @Override
    public void addColumnCounter(String name,String nullable,String type) {
        counterHistoryMapper.addColumnCounter(name,nullable,type);
    }

    @Override
    public List<JSONObject> getCounterList() {
        return counterMapper.getCounterList();
    }

    @Override
    public void deleteColumnCounter(String colum) {
        counterHistoryMapper.deleteColumnCounter(colum);
    }

    @Override
    public int deleteCounters() {
        return counterMapper.deleteCounters();
    }

    @Override
    public JSONObject getCounterColumnAttribute(String name) {
        return counterHistoryMapper.getCounterColumnAttribute(name);
    }

    @Override
    public void addGroupQuotaColumn(String formula) {
        quotaHistoryGroupMapper.addColumn(formula);
    }

    @Override
    public void addNodeQuotaColumn(String formula) {
        quotaHistoryNodeMapper.addColumn(formula);
    }

    @Override
    public void addCellQuotaColumn(String formula) {
        quotaHistoryCellMapper.addColumn(formula);
    }

    @Override
    public void deleteGroupQuotaColumn(String quotaName) {
        quotaHistoryGroupMapper.deleteColumn(quotaName);
    }

    @Override
    public void deleteNodeQuotaColumn(String quotaName) {
        quotaHistoryNodeMapper.deleteColumn(quotaName);
    }

    @Override
    public void deleteCellQuotaColumn(String quotaName) {
        quotaHistoryCellMapper.deleteColumn(quotaName);
    }

    @Override
    public int deleteGroupThresholdByName(String quotaName) {
        return quotaThresholdGroupMapper.deleteThresholdByName(quotaName);
    }

    @Override
    public int deleteNodeThresholdByName(String quotaName) {
        return quotaThresholdNodeMapper.deleteThresholdByName(quotaName);
    }

    @Override
    public int deleteCellThresholdByName(String quotaName) {
        return quotaThresholdCellMapper.deleteThresholdByName(quotaName);
    }

    @Override
    public int addGroupThreshold(JSONObject threshold) {
        return quotaThresholdGroupMapper.addThreshold(threshold);
    }

    @Override
    public int addNodeThreshold(JSONObject threshold) {
        return quotaThresholdNodeMapper.addThreshold(threshold);
    }

    @Override
    public int addCellThreshold(JSONObject threshold) {
        return quotaThresholdCellMapper.addThreshold(threshold);
    }

    @Override
    public void setGroupQuotaColumn(String oldquotaName,String quotaName) {

        quotaHistoryGroupMapper.setColumn(oldquotaName,quotaName);
    }

    @Override
    public void setNodeQuotaColumn(String oldquotaName,String quotaName) {
        quotaHistoryNodeMapper.setColumn(oldquotaName,quotaName);
    }

    @Override
    public void setCellQuotaColumn(String oldquotaName,String quotaName) {
        quotaHistoryCellMapper.setColumn(oldquotaName,quotaName);
    }

    @Override
    public String getquotaName(String expression) {
        return formulaMapper.getquotaName(expression);
    }

    @Override
    public List<JSONObject> getCellFormula(Date start, Date end, String cell) {
        return quotaHistoryCellMapper.getCellFormula(start,end,cell);
    }

}
