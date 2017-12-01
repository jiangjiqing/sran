package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.*;
import com.hongshen.sran_service.service.QuotaService;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by poplar on 11/13/17.
 */
@Service
public class QuotaService_Unicom_Lte implements QuotaService {
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
    public JSONObject getGroupQuotaByName(String groupName) {
        return quotaHistoryGroupMapper.getQuotaByName(groupName);
    }

    @Override
    public JSONObject getNodeQuotaByName(String nodeName){
        return quotaHistoryNodeMapper.getQuotaByName(nodeName);
    }

    @Override
    public JSONObject getCellQuotaByName(String cellName){
        return quotaHistoryCellMapper.getQuotaByName(cellName);
    }

    @Override
    public JSONObject getGroupLevel(String groupName) {
        return quotaHistoryGroupMapper.getLevel(groupName);
    }

    @Override
    public JSONObject getNodeLevel(String nodeName) {
        return quotaHistoryNodeMapper.getLevel(nodeName);
    }

    @Override
    public JSONObject getCellLevel(String cellName) {
        return quotaHistoryCellMapper.getLevel(cellName);
    }

    @Override
    public Integer setGroup(JSONObject quotaThres) {
        return quotaThresholdGroupMapper.setGroup(quotaThres);
    }

    @Override
    public Integer setNode(JSONObject quotaThres) {
        return quotaThresholdNodeMapper.setNode(quotaThres);
    }

    @Override
    public Integer setCell(JSONObject quotaThres) {
        return quotaThresholdCellMapper.setCell(quotaThres);
    }

    @Override
    public List<JSONObject>  getQuotas(Date start, Date end) {
        return quotaHistoryGroupMapper.getQuos(start,end);
    }
//    @Override
//    public Map<String, Object> getQuotaInfo() {
//        return unicomQuotaHistoryGroupLteMapper.getQuotaInfo();
//    }
   /*add
    @Override
    public List<JSONObject> getNodes(String userName, String groupName, String time) {

        List<JSONObject> results = new ArrayList<>();

        results = quotaHistoryNodeLteMapper.getNodeHistoryDataLte(groupName, time);

        if (!results.isEmpty()) {

            return results;
        }

        return results;
    }*/
	
   /*add
    @Override
    public List<JSONObject> getCells(String userName, String nodeName, String time) {

        List<JSONObject> results = new ArrayList<>();

        quotaHistoryCellLteMapper.getCellHistoryDataLte(nodeName, time);

        if (!results.isEmpty()) {

            return results;
        }

        return results;
    }*/
}
