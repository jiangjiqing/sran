package com.hongshen.sran_service.service;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.entity.UnicomUserAuthorityWcdma;

/**
 * Created by poplar on 11/10/17.
 */
public interface UserAuthorityWcdma {
    UnicomUserAuthorityWcdma getUserInfo();

    void addUser(JSONObject param);
}
