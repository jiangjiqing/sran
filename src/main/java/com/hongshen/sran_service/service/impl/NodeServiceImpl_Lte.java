package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.UnicomAlarmLteMapper;
import com.hongshen.sran_service.dao.UnicomNodeLteMapper;
import com.hongshen.sran_service.dao.UnicomQuotaHistoryNodeLteMapper;
import com.hongshen.sran_service.entity.UnicomAlarmLte;
import com.hongshen.sran_service.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NodeServiceImpl_Lte implements NodeService{
//
//    @Autowired
//    private UnicomNodeLteMapper nodeLteMapper;
//
//    @Autowired
//    private UnicomQuotaHistoryNodeLteMapper quotaHistoryNodeLteMapper;
//
//    @Autowired
//    private UnicomAlarmLteMapper alarmLteMapper;
//
//    @Override
//    public List<JSONObject> getNodes(String userName, String groupName, String time) {
//
//        List<JSONObject> results = new ArrayList<>();
//
//        results = quotaHistoryNodeLteMapper.getNodeHistoryDataLte(groupName, time);
//
//        if (!results.isEmpty()) {
//
//            return results;
//        }
//
//        return results;
//    }
//
//    @Override
//    public JSONObject getNodeAlarmByNodeName(String nodeName) {
//
//        JSONObject result = new JSONObject();
//
//        UnicomAlarmLte alarmLte = alarmLteMapper.getNodeAlarmByNodeNameLte(nodeName);
//
//        return result;
//    }
}
