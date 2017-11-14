package com.hongshen.sran_service.service.util.impl;

import com.hongshen.sran_service.service.CellService;
import com.hongshen.sran_service.service.AlarmLibrary;
import com.hongshen.sran_service.service.DataProviderBase;
import com.hongshen.sran_service.service.ElementTopology;
import com.hongshen.sran_service.service.GroupService;
import com.hongshen.sran_service.service.NodeService;
import com.hongshen.sran_service.service.impl.CellServiceImpl_Wcdma;
import com.hongshen.sran_service.service.impl.AlarmLibrary_Wcdma;
import com.hongshen.sran_service.service.impl.DataProvider_Unicom_Wcdma;
import com.hongshen.sran_service.service.impl.ElementTopology_Wcdma;
import com.hongshen.sran_service.service.impl.GroupServiceImpl_Wcdma;
import com.hongshen.sran_service.service.impl.NodeServiceImpl_Wcdma;
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
    private ElementTopology_Wcdma elementTopology_Wcdma;
    @Autowired
    private AlarmLibrary_Wcdma alarmLibrary_Wcdma;
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
    public ElementTopology getElementTopologyr() {
        return elementTopology_Wcdma;
    }

    @Override
    public AlarmLibrary getAlarmLibrary() {
        return alarmLibrary_Wcdma;
    }

    @Override
    public AlarmLibrary getSpecifiedLibrary() {
        return alarmLibrary_Wcdma;
    }

    @Override
    public AlarmLibrary updateSpecifiedLibrary() {
        return alarmLibrary_Wcdma;
    }

    @Override
    public AlarmLibrary addSpecifiedLibrary() {
        return alarmLibrary_Wcdma;
    }
//new service

}
