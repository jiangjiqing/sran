package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.UnicomUserAuhorityLteMapper;
import com.hongshen.sran_service.dao.UnicomUserAuthorityWcdmaMapper;
import com.hongshen.sran_service.service.util.Httpclient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by poplar on 11/14/17.
 */
@Service
public class UserAgentService {
    @Autowired
    private static Httpclient httpclient;
    @Autowired
    private static UnicomUserAuhorityLteMapper userAuhorityLteMapper;
    @Autowired
    private static UnicomUserAuthorityWcdmaMapper userAuhorityWcdmaMapper;
//    @Autowired
//    private UserMapper userMapper;

    public List getRoleList() {
        String method = "getRole";
        List a = null;
        try {
           a =  httpclient.getRole(method);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return a;
    }

    public Map<String,Object> getUserInfo() {
//        httpclient.
        String method = "getUser";
        Map A = null;
        try {
            A = httpclient.getUser(method);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return A;
    }

    public static int addUser(JSONObject param) {
        try {
           int i =  httpclient.addUser(param);
           if (i == 1){
               return 1;
           }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int updateUser(JSONObject param) {

        try {
            int i =  httpclient.updateUser(param);
            if (i == 1){
                return 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int deleteUser(String loginName) {

        try {
            int i =  httpclient.deleteUser(loginName);
            if (i == 1){
                return 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public List<JSONObject> getLteAuth(String name) {
        return userAuhorityLteMapper.getLteAuth(name);
    }

    public List<JSONObject> getWcdmaAuth(String name) {
        return userAuhorityWcdmaMapper.getWcdmaAuth(name);
    }


    public static int addLteAuthory(String loginName, String authorityName, String list) {
       return userAuhorityLteMapper.addLteAuthory(loginName,authorityName,list);
    }

    public static int addWcdmaAuthory(String loginName, String authorityName, String list) {
        return userAuhorityWcdmaMapper.addWcdmaAuthory(loginName,authorityName,list);
    }
}
