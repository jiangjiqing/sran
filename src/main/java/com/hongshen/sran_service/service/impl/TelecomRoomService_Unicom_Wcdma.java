package com.hongshen.sran_service.service.impl;

import com.hongshen.sran_service.dao.UnicomGroupWcdmaMapper;
import com.hongshen.sran_service.service.TelecomRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by poplar on 11/14/17.
 */
@Service
public class TelecomRoomService_Unicom_Wcdma implements TelecomRoomService {
    @Autowired
    private UnicomGroupWcdmaMapper unicomGroupWcdmaMapper;
    @Override
    public Map<String, Object> getElementTopologyr() {
        return unicomGroupWcdmaMapper.getElementTopologyr();
    }
}
