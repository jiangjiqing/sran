package com.hongshen.sran_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.dao.UnicomUserAuthorityWcdmaMapper;
import com.hongshen.sran_service.entity.UnicomUserAuthorityWcdma;
import com.hongshen.sran_service.service.UserAuthorityWcdma;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by poplar on 11/10/17.
 */
public class UserAuthority_Wcdma implements UserAuthorityWcdma {
    @Autowired
    private UnicomUserAuthorityWcdmaMapper unicomUserAuthorityWcdmaMapper;
    @Override
    public UnicomUserAuthorityWcdma getUserInfo() {
        return unicomUserAuthorityWcdmaMapper.getUserInfo();
    }

    @Override
    public void addUser(JSONObject param) {
        unicomUserAuthorityWcdmaMapper.addUserd(param);
    }
}
