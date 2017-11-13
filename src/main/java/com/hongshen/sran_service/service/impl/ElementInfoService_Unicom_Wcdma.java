package com.hongshen.sran_service.service.impl;

import com.hongshen.sran_service.dao.UnicomGroupWcdmaMapper;
import com.hongshen.sran_service.service.ElementInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by poplar on 11/13/17.
 */
@Service
public class ElementInfoService_Unicom_Wcdma implements ElementInfoService {
    @Autowired
    private UnicomGroupWcdmaMapper unicomGroupWcdmaMapper;
    @Override
    public Map<String, Object> getGroupInfo() {
        return unicomGroupWcdmaMapper.getGroupInfo();
    }
}
