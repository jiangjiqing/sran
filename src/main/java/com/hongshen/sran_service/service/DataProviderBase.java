package com.hongshen.sran_service.service;

import com.hongshen.sran_service.entity.Role;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * Created by poplar on 17-10-30.
 */

public interface DataProviderBase {
    Role getGroupQuotaInfo(int id);

    Map<String,Object> getProtect();
}
