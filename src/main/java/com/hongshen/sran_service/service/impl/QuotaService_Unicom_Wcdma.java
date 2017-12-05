package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.*;
import com.hongshen.sran_service.entity.UnicomQuotaHistoryGroupWcdma;
import com.hongshen.sran_service.entity.UnicomQuotaThresholdNodeWcdma;
import com.hongshen.sran_service.service.QuotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by poplar on 11/13/17.
 */
@Service
public class QuotaService_Unicom_Wcdma implements QuotaService {

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

//    @Override
//    public Map<String, Object> getQuotaInfo() {
//        return unicomQuotaHistoryGroupWcdmaMapper.getQuotaInfo();
	
	/*add
	@Override
    public List<JSONObject> getGroups(String userName, String time) {

        List<JSONObject> results = new ArrayList<>();

        String authList = null;

        authList = authorityWcdmaMapper.getAuthorityList(userName);

        if (authList != null) {

            String [] list = authList.split(",");

            for (String node : list) {

                JSONObject result = quotaWcdmaMapper.getHistoryData(node, time);

                if (!result.isEmpty()) {

                    results.add(result);
                }
            }

        } else {

            return results;
        }

        return results;
    }*/
	
	/*del
    @Override
    public List<JSONObject> getNodes(String userName, String groupName, String time) {

        List<JSONObject> results = new ArrayList<>();

        results = quotaHistoryNodeWcdmaMapper.getNodeHistoryDataWcdma(groupName, time);

        if (!results.isEmpty()) {

            return results;
        }

        return results;
    }*/
	
	/*del
    @Override
    public List<JSONObject> getCells(String userName, String nodeName, String time) {

        List<JSONObject> results = new ArrayList<>();

        quotaHistoryCellWcdmaMapper.getCellHistoryDataWcdma(nodeName, time);

        if (!results.isEmpty()) {

            return results;
        }

        return results;
    }*/
	


	
}
