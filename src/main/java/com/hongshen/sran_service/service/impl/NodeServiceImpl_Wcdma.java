package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.UnicomAlarmWcdmaMapper;
import com.hongshen.sran_service.dao.UnicomNodeWcdmaMapper;
import com.hongshen.sran_service.dao.UnicomQuotaHistoryNodeWcdmaMapper;
import com.hongshen.sran_service.entity.UnicomAlarmWcdma;
import com.hongshen.sran_service.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NodeServiceImpl_Wcdma implements NodeService{
//
//    @Autowired
//    private UnicomNodeWcdmaMapper nodeWcdmaMapper;
//
//    @Autowired
//    private UnicomQuotaHistoryNodeWcdmaMapper quotaHistoryNodeWcdmaMapper;
//
//    @Autowired
//    private UnicomAlarmWcdmaMapper alarmWcdmaMapper;
//
//    @Override
//    public List<JSONObject> getNodes(String userName, String groupName, String time) {
//
//        List<JSONObject> results = new ArrayList<>();
//
//        results = quotaHistoryNodeWcdmaMapper.getNodeHistoryDataWcdma(groupName, time);
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
//        UnicomAlarmWcdma alarmWcdma = alarmWcdmaMapper.getNodeAlarmByNodeNameWcdma(nodeName);
//
//        return result;
//    }
}
