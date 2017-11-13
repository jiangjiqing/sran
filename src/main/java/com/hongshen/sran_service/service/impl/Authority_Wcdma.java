package com.hongshen.sran_service.service.impl;

import com.hongshen.sran_service.dao.UnicomAuthorityWcdmaMapper;
import com.hongshen.sran_service.entity.UnicomAuthorityWcdma;
import com.hongshen.sran_service.service.AuthorityWcdma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by poplar on 11/10/17.
 */
@Service
public class Authority_Wcdma implements AuthorityWcdma {
    @Autowired
    private UnicomAuthorityWcdmaMapper UnicomAuthorityWcdmaMapper;
    @Override
    public UnicomAuthorityWcdma getUserLists() {
        return UnicomAuthorityWcdmaMapper.getUserLists();
    }
}
