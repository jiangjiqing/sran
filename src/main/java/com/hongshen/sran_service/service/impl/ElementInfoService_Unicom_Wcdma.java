package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.UnicomProtectWcdmaMapper;
import com.hongshen.sran_service.service.ElementInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created by poplar on 11/13/17.
 */
@Service
public class ElementInfoService_Unicom_Wcdma implements ElementInfoService {
	
    @Autowired
    private UnicomProtectWcdmaMapper unicomProtectMapper;
	
    @Override
    public List<JSONObject> getProtectList() {
        return unicomProtectMapper.getProtectList();
    }
//    @Autowired
//    private UnicomGroupWcdmaMapper unicomGroupWcdmaMapper;
//    @Autowired
//    private UnicomProtectWcdmaMapper unicomProtectWcdmaMapper;
//    @Override
//    public List<JSONObject> getGroupInfo(String name) {
//        return unicomGroupWcdmaMapper.getGroupInfo(name);
//    }
//
//    @Override
//    public List<String> getProtect() {
//
//        return unicomProtectWcdmaMapper.getProtect();
//    }
}
