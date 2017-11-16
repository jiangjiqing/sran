package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.UnicomAlarmWcdmaMapper;
import com.hongshen.sran_service.dao.UnicomCellWcdmaMapper;
import com.hongshen.sran_service.dao.UnicomQuotaHistoryCellWcdmaMapper;
import com.hongshen.sran_service.service.CellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CellServiceImpl_Wcdma implements CellService{

//    @Autowired
//    private UnicomCellWcdmaMapper cellWcdmaMapper;
//
//    @Autowired
//    private UnicomQuotaHistoryCellWcdmaMapper quotaHistoryCellWcdmaMapper;
//
//    @Autowired
//    private UnicomAlarmWcdmaMapper alarmWcdmaMapper;
//
//    @Override
//    public List<JSONObject> getCells(String userName, String nodeName, String time) {
//
//        List<JSONObject> results = new ArrayList<>();
//
//        quotaHistoryCellWcdmaMapper.getCellHistoryDataWcdma(nodeName, time);
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
//    public JSONObject getCellAlarmByCellName(String cellName) {
//
//        JSONObject result = new JSONObject();
//
//        result = alarmWcdmaMapper.getCellAlarmByCellNameWcdma(cellName);
//
//        return result;
//    }
}
