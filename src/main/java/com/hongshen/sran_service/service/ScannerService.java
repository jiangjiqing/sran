package com.hongshen.sran_service.service;

import com.alibaba.fastjson.JSONObject;

public interface ScannerService {

    String cellCalculation(String time);

    JSONObject nodeCalculation(String time);

    String groupCalculation(JSONObject params, String time);
}
