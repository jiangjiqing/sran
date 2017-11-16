package com.hongshen.sran_service.service.util;

import com.hongshen.sran_service.service.*;

/**
 * Created by poplar on 17-10-30.
 */

public interface NetObjBase {

    TelecomRoomService getTelecomRoomService();

//    AlarmLibService getAlarmLibService();

    TaskService getTaskService();

    ElementInfoService getElementInfoService();
	
}
