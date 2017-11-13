package com.hongshen.sran_service.service.impl;

import com.hongshen.sran_service.dao.UnicomAlarmLibraryMapper;
import com.hongshen.sran_service.service.AlarmLibService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by poplar on 11/13/17.
 */
@Service
public class AlarmLibService_Unicom implements AlarmLibService {
    @Autowired
    private UnicomAlarmLibraryMapper unicomAlarmLibraryMapper;
    @Override
    public Map<String, Object> getAlarmInfo() {
        return unicomAlarmLibraryMapper.getAlarmInfo();
    }
}
