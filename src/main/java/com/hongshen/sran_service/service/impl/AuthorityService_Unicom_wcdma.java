package com.hongshen.sran_service.service.impl;

import com.hongshen.sran_service.dao.UnicomAuthorityWcdmaMapper;
import com.hongshen.sran_service.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by poplar on 11/13/17.
 */
@Service
public class AuthorityService_Unicom_wcdma implements AuthorityService {
    @Autowired
    private UnicomAuthorityWcdmaMapper authorityWcdmaMapper;
    @Override
    public Map<String, Object> getUserAuthorty(String name) {
        return authorityWcdmaMapper.getUserAuthorty(name);
    }
}
