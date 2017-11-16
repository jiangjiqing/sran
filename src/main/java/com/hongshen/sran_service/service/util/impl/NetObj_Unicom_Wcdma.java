package com.hongshen.sran_service.service.util.impl;

import com.hongshen.sran_service.service.*;
import com.hongshen.sran_service.service.impl.*;
import com.hongshen.sran_service.service.util.NetObjBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by poplar on 17-10-30.
 */
@Controller
public class NetObj_Unicom_Wcdma implements NetObjBase {

//    @Autowired
//    private AlarmLibService_Unicom_Wcdma alarmLibService;

    @Autowired
    private TelecomRoomService_Unicom_Wcdma telecomRoomService;

    @Autowired
    private TaskService_Unicom_Wcdma taskService;
	
    @Autowired
    private ElementInfoService_Unicom_Wcdma elementInfoService;
	

    @Override
    public TelecomRoomService getTelecomRoomService() {
        return telecomRoomService;
    }

//    @Override
//    public AlarmLibService getAlarmLibService() {
//        return alarmLibService;
//    }

    @Override
    public TaskService getTaskService() {
        return taskService;
    }

    @Override
    public ElementInfoService getElementInfoService() {
        return elementInfoService;
    }

}
