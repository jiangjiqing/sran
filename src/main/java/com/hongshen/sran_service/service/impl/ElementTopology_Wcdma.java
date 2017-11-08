package com.hongshen.sran_service.service.impl;

import com.hongshen.sran_service.dao.UnicomGroupWcdmaMapper;
import com.hongshen.sran_service.service.ElementTopology;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by poplar on 17-11-8.
 */
@Service
public class ElementTopology_Wcdma implements ElementTopology{
    @Autowired
    private UnicomGroupWcdmaMapper unicomGroupWcdmaMapper;
    @Override
    public Map<String, Object> getElementTopologyr() {
        return unicomGroupWcdmaMapper.getElementTopologyr();
    }
}
