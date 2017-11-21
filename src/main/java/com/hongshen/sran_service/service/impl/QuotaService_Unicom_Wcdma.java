package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.UnicomQuotaHistoryGroupWcdmaMapper;
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
    private UnicomQuotaHistoryGroupWcdmaMapper quotaGroupMapper;


    @Override
    public JSONObject getGroupQuotaByGroupName(String groupName) {
        return quotaGroupMapper.getGroupQuotaByGroupName(groupName);
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
