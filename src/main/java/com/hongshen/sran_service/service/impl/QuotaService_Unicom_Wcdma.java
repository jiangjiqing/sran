package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.UnicomQuotaHistoryCellWcdmaMapper;
import com.hongshen.sran_service.dao.UnicomQuotaHistoryGroupWcdmaMapper;
import com.hongshen.sran_service.dao.UnicomQuotaHistoryNodeWcdmaMapper;
import com.hongshen.sran_service.entity.UnicomQuotaHistoryGroupWcdma;
import com.hongshen.sran_service.service.QuotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public int getGroupLevelByName(String groupName) {
        return quotaHistoryGroupMapper.getLevelByName(groupName);
    }

    @Override
    public int getNodeLevelByName(String nodeName) {
        return quotaHistoryNodeMapper.getLevelByName(nodeName);
    }

    @Override
    public int getCellLevelByName(String cellName) {
        return quotaHistoryCellMapper.getLevelByName(cellName);
    }

    @Override
    public JSONObject getNodeLevel(String nodeName) {
        return quotaHistoryNodeMapper.getNodeLevel(nodeName);
    }

    @Override
    public JSONObject getCellLevel(String cellName) {
        return  quotaHistoryCellMapper.getCellLevel(cellName);
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
