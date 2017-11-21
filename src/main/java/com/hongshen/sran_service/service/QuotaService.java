package com.hongshen.sran_service.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by poplar on 11/13/17.
 */
public interface QuotaService {
//    Map<String, Object> getQuotaInfo();

    JSONObject getGroupQuotaByGroupName(String groupName);
}
