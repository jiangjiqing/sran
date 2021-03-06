package com.hongshen.sran_service.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by poplar on 11/13/17.
 */
public interface AuthorityService {
    List<JSONObject> getAuthorityList();

    int updateAuthority(String authorityName, JSONObject param);

    int addAuthority(JSONObject param);

    int deleteAuthority(String authorityName);

    JSONObject getAuthByName(JSONObject param);

    List<JSONObject> getAuthorityByLoginNameList(String loginName);


//    Map<String,Object> getUserAuthorty(String name);
}
