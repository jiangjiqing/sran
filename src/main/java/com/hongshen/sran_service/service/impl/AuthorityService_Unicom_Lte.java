package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.UnicomAuthorityLteMapper;
import com.hongshen.sran_service.dao.UnicomAuthorityWcdmaMapper;
import com.hongshen.sran_service.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by poplar on 11/13/17.
 */
@Service
public class AuthorityService_Unicom_Lte implements AuthorityService {

    @Autowired
    private UnicomAuthorityLteMapper authorityMapper;

    @Override
    public List<JSONObject> getAuthorityList() {
        return authorityMapper.getAuthorityList();
    }

    @Override
    public int updateAuthority(String authorityName, JSONObject param) {
        return authorityMapper.updateAuthority(authorityName,param);
    }

    @Override
    public int addAuthority(JSONObject param) {
        return authorityMapper.addAuthority(param);
    }

    @Override
    public int deleteAuthority(String authorityName) {
        return authorityMapper.deleteAuthority(authorityName);
    }

    @Override
    public JSONObject getAuthByName(JSONObject param) {
        return authorityMapper.getAuthByName(param);
    }

//    @Autowired
//    private UnicomAuthorityLteMapper authorityMapper;

//    @Override
//    public Map<String, Object> getUserAuthorty(String name) {
//        return null;
//    }
}
