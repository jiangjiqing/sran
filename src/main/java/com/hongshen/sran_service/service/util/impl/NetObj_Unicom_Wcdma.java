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
//    private AuthorityService_Unicom_Wcdma authorityService;

    @Autowired
    private CacheService_Unicom_Wcdma cacheService;

    @Autowired
    private  AlarmService_Unicom_Wcdma alarmService;

    //    @Autowired
//    private AlarmLibService_Unicom_Wcdma alarmLibService;

    @Autowired
    private ElementInfoService_Unicom_Wcdma elementInfoService;

    @Autowired
    private TelecomRoomService_Unicom_Wcdma telecomRoomService;

    @Autowired
    private TaskService_Unicom_Wcdma taskService;

    @Autowired
    private AuthorityService_Unicom_Wcdma authorityService;

    @Autowired
    private QuotaService_Unicom_Wcdma quotaService;

    @Autowired
    private ScannerService_Unicom_Wcdma scannerService;

    @Override
    public AuthorityService getAuthorityService(){ return authorityService; }

    @Override
    public CacheService getCacheService() { return cacheService; }

    @Override
    public QuotaService getQuotaService() { return quotaService; }

    @Override
    public AlarmService getAlarmService() { return alarmService; }

    //    @Override
//    public AlarmLibService getAlarmLibService() {
//        return alarmLibService;
//    }

    @Override
    public ElementInfoService getElementInfoService() {
        return elementInfoService;
    }

    @Override
    public TelecomRoomService getTelecomRoomService() {
        return telecomRoomService;
    }

    @Override
    public TaskService getTaskService() {
        return taskService;
    }

    @Override
    public QuotaService quotaService() {
        return quotaService;
    }

    @Override
    public ScannerService getScannerService() {

        return scannerService;
    }


}
