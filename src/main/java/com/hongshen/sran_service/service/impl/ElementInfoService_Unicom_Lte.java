package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.UnicomGroupWcdmaMapper;
import com.hongshen.sran_service.dao.UnicomProtectLteMapper;
import com.hongshen.sran_service.dao.UnicomQuotaGroupLteMapper;
import com.hongshen.sran_service.service.ElementInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by poplar on 11/13/17.
 */
@Service
public class ElementInfoService_Unicom_Lte implements ElementInfoService {
    @Override
    public List<JSONObject> getProtect() {
        return null;
    }
//    @Autowired
//    private UnicomProtectLteMapper unicomProtectLteMapper;
//    @Autowired
//    private UnicomGroupWcdmaMapper unicomGroupWcdmaMapper;
//    @Override
//    public List<JSONObject> getGroupInfo(String name) {
//        return unicomGroupWcdmaMapper.getGroupInfo(name);
//    }
//
//    @Override
//    public List<String> getProtect() {
//
//        return unicomProtectLteMapper.getProtect();
//    }
}
