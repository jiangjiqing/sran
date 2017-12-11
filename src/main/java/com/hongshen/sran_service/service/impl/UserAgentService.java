package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.UnicomUserAuhorityLteMapper;
import com.hongshen.sran_service.dao.UnicomUserAuthorityWcdmaMapper;
import com.hongshen.sran_service.dao.UserMapper;
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
    private Httpclient httpclient;

    @Autowired
    private UnicomUserAuhorityLteMapper userAuhorityLteMapper;

    @Autowired
    private UnicomUserAuthorityWcdmaMapper userAuhorityWcdmaMapper;

    @Autowired
    private UserMapper userMapper; // TODO delete

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



    public int addUser(JSONObject param) {
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

    public int updateUser(JSONObject param) {

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

    public int deleteUser(String loginName) {

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

    public List<String> getLteAuth(String name) {
        return userAuhorityLteMapper.getLteAuth(name);
    }

    public List<String> getWcdmaAuth(String name) {
        return userAuhorityWcdmaMapper.getWcdmaAuth(name);
    }

    public int addLteUserAuthory(String loginName, String authorityName) {
        return userAuhorityLteMapper.addLteUserAuthory(authorityName,loginName);
    }

    public int addWcdmaUserAuthory(String loginName, String authorityName) {
        return userAuhorityWcdmaMapper.addWcdmaUserAuthory(authorityName,loginName);

    }

    public int updateLteUserAuthory(String loginName, String authorityName) {
        return userAuhorityLteMapper.updateLteUserAuthory(authorityName,loginName);

    }

    public int updateWcdmaUserAuthory(String loginName, String authorityName) {
        return userAuhorityWcdmaMapper.updateWcdmaUserAuthory(authorityName,loginName);

    }

    public int deleteLteUserAuthory(String loginName) {
        return userAuhorityLteMapper.deleteLteUserAuthory(loginName);

    }

    public int deletewcdmaUserAuthory(String loginName) {
        return userAuhorityWcdmaMapper.deletewcdmaUserAuthory(loginName);

    }

    public List<JSONObject> getUserList(){
        // TODO delete
        return userMapper.getUserList();
    }
}
