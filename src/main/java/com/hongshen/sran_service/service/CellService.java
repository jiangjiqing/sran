package com.hongshen.sran_service.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface CellService {

    List<JSONObject> getCells(String userName, String nodeName, String time);
}