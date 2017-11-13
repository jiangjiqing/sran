package com.hongshen.sran_service.service.impl;

import com.hongshen.sran_service.dao.UnicomAuthorityLteMapper;
import com.hongshen.sran_service.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by poplar on 11/13/17.
 */
@Service
public class AuthorityService_Unicom_Lte implements AuthorityService {
    @Autowired
    private UnicomAuthorityLteMapper unicomAuthorityLteMapper;
    @Override
    public Map<String, Object> getUserAuthorty(String name) {
        return unicomAuthorityLteMapper.getUserAuthorty(name);
    }
}
