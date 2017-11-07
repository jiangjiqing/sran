package com.hongshen.sran_service.service.impl;

import com.hongshen.sran_service.service.DataProviderBase;
import com.hongshen.sran_service.dao.RoleMapper;
import com.hongshen.sran_service.dao.UnicomProtectLteMapper;
import com.hongshen.sran_service.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * Created by poplar on 17-10-30.
 */
@Service
public class DataProvider_Unicom_Lte implements DataProviderBase {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UnicomProtectLteMapper unicomProtectLteMapper;
    @Override
    public Role getGroupQuotaInfo(int id) {
        System.out.println(id);
        Role role1 =roleMapper.selectByPrimaryKey(id);
        return role1;
    }

    @Override
    public Map<String, Object> getProtect() {
        return unicomProtectLteMapper.getProtect();
    }

}
