package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.UnicomAlarmLteMapper;
import com.hongshen.sran_service.dao.UnicomCellLteMapper;
import com.hongshen.sran_service.dao.UnicomQuotaHistoryCellLteMapper;
import com.hongshen.sran_service.service.CellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CellServiceImpl_Lte implements CellService{

    @Autowired
    private UnicomCellLteMapper cellLteMapper;

    @Autowired
    private UnicomQuotaHistoryCellLteMapper quotaHistoryCellLteMapper;

    @Autowired
    private UnicomAlarmLteMapper alarmLteMapper;

    @Override
    public List<JSONObject> getCells(String userName, String nodeName, String time) {

        List<JSONObject> results = new ArrayList<>();

        quotaHistoryCellLteMapper.getCellHistoryDataLte(nodeName, time);

        if (!results.isEmpty()) {

            return results;
        }

        return results;
    }

    @Override
    public JSONObject getCellAlarmByCellName(String cellName) {

        JSONObject result = new JSONObject();

        result = alarmLteMapper.getCellAlarmByCellNameLte(cellName);

        return result;
    }
}
