package com.hongshen.sran_service.service.impl;

import com.hongshen.sran_service.dao.UnicomQuotaHistoryGroupWcdmaMapper;
import com.hongshen.sran_service.service.QuotaServuce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by poplar on 11/13/17.
 */
@Service
public class QuotaService_Unicom_Wcdma implements QuotaServuce {
    @Autowired
    private UnicomQuotaHistoryGroupWcdmaMapper unicomQuotaHistoryGroupWcdmaMapper;
    @Override
    public Map<String, Object> getQuotaInfo() {
        return unicomQuotaHistoryGroupWcdmaMapper.getQuotaInfo();
    }
}
