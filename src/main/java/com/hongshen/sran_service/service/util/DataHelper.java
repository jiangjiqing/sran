package com.hongshen.sran_service.service.util;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataHelper {

    public static Map<String,JSONObject> JsonListToJsonMap(List<JSONObject> params,String key){

        Map<String, JSONObject> resultMap = new HashMap<>();

        for (JSONObject param : params) {
            resultMap.put(param.getString(key), param);
        }

        return resultMap;
    }
}
