package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.UnicomGroupWcdmaMapper;
import com.hongshen.sran_service.dao.UnicomNodeWcdmaMapper;
import com.hongshen.sran_service.service.ElementInfoServiceQuotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by poplar on 11/20/17.
 */
@Service
public class ElementInfoServiceQuotaService_unicom_wcdma implements ElementInfoServiceQuotaService {
    @Autowired
    private UnicomGroupWcdmaMapper unicomGroupMapper;

    @Autowired
    private UnicomNodeWcdmaMapper unicomNodeMapper;

    @Override
    public List<JSONObject> getquota() {
        return unicomGroupMapper.getQuta();
    }

    @Override
    public List<JSONObject> getNodeByName(String groupName) {
        return unicomNodeMapper.getNodeByName(groupName);
    }

    @Override
    public List<JSONObject> getNodeList(String groupName) {
        return unicomNodeMapper.getNodeList(groupName);
    }

    @Override
    public JSONObject getGroupByName(String groupName) {
        return unicomGroupMapper.getGroupByName(groupName);
    }

}
