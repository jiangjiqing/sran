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

    @Autowired
    private DataProvider_Unicom_Wcdma dataProvider_Unicom_Wcdma;

    @Autowired
    private GroupServiceImpl_Wcdma groupServiceImpl_wcdma;

    @Autowired
    private NodeServiceImpl_Wcdma nodeServiceImpl_wcdma;

    @Autowired
    private CellServiceImpl_Wcdma cellServiceImpl_wcdma;

    @Autowired
    private AlarmLibService_Unicom alarmLibService_Unicom;

    @Autowired
    private TelecomRoomService_Unicom_Wcdma telecomRoomService_unicom_wcdma;

    @Autowired
    private TaskService_Unicom_Wcdma taskService_unicom_wcdma;

    @Override
    public DataProviderBase getDataProvider() {
        return dataProvider_Unicom_Wcdma;
    }

    @Override
    public GroupService getGroupService() {

        return groupServiceImpl_wcdma;
    }

    @Override
    public NodeService getNodeService() {

        return nodeServiceImpl_wcdma;
    }

    @Override
    public CellService getCellService() {

        return cellServiceImpl_wcdma;
    }

    @Override
    public TelecomRoomService getElementTopologyr() {
        return telecomRoomService_unicom_wcdma;
    }

    @Override
    public AlarmLibService getAlarmLibrary() {
        return alarmLibService_Unicom;
    }

    @Override
    public AlarmLibService getSpecifiedLibrary() {
        return alarmLibService_Unicom;
    }

    @Override
    public AlarmLibService updateSpecifiedLibrary() {
        return alarmLibService_Unicom;
    }

    @Override
    public AlarmLibService addSpecifiedLibrary() {
        return alarmLibService_Unicom;
    }

    @Override
    public TaskService getTaskInfo() {
        return taskService_unicom_wcdma;
    }
//new service

}
