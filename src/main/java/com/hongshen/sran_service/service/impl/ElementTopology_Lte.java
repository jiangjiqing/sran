package com.hongshen.sran_service.service.impl;

import com.hongshen.sran_service.service.ElementTopology;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by poplar on 17-11-8.
 */
@Service
public class ElementTopology_Lte implements ElementTopology{

    @Override
    public Map<String, Object> getElementTopologyr() {
        Map<String,Object> map = null;
        map.put("result",null);
        return map;
    }
}
