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
public class NetObj_Unicom_Lte implements NetObjBase {

    @Autowired
    private DataProvider_Unicom_Lte dataProvider_Unicom_Lte;

    @Autowired
    private GroupServiceImpl_Lte groupServiceImpl_lte;

    @Autowired
    private NodeServiceImpl_Lte nodeServiceImpl_lte;

    @Autowired
    private CellServiceImpl_Lte cellServiceImpl_lte;

    @Autowired
    private AlarmLibService_Unicom alarmLibService_Unicom;

    @Autowired
    private TelecomRoomService_Unicom_Wcdma telecomRoomService_unicom_wcdma;

    @Override
    public DataProviderBase getDataProvider() {
        return dataProvider_Unicom_Lte;
    }

    @Override
    public GroupService getGroupService() {

        return groupServiceImpl_lte;
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
    public NodeService getNodeService() {
        return null;
    }

    @Override
    public CellService getCellService() {

        return cellServiceImpl_lte;
    }

    @Override
    public AlarmLibService addSpecifiedLibrary() {
        return alarmLibService_Unicom;
    }
}
