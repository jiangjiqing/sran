package com.hongshen.sran_service.service.util;

import com.hongshen.sran_service.service.*;

/**
 * Created by poplar on 17-10-30.
 */

public interface NetObjBase {

//    getAuthorityService();

    QuotaService getQuotaService();

    AlarmService getAlarmService();

//    AlarmLibService getAlarmLibService();

    ElementInfoService getElementInfoService();

    TelecomRoomService getTelecomRoomService();

    TaskService getTaskService();
	
}
