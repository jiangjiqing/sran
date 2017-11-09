package com.hongshen.sran_service.service.util.impl;

import com.hongshen.sran_service.service.CellService;
import com.hongshen.sran_service.service.AlarmLibrary;
import com.hongshen.sran_service.service.DataProviderBase;
import com.hongshen.sran_service.service.ElementTopology;
import com.hongshen.sran_service.service.GroupService;
import com.hongshen.sran_service.service.NodeService;
import com.hongshen.sran_service.service.impl.CellServiceImpl_Lte;
import com.hongshen.sran_service.service.impl.AlarmLibrary_Lte;
import com.hongshen.sran_service.service.impl.DataProvider_Unicom_Lte;
import com.hongshen.sran_service.service.impl.ElementTopology_Lte;
import com.hongshen.sran_service.service.impl.GroupServiceImpl_Lte;
import com.hongshen.sran_service.service.impl.NodeServiceImpl_Lte;
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
    private ElementTopology_Lte elementTopology_Lte;

    @Autowired
    private AlarmLibrary_Lte alarmLibrary_Lte;

    @Override
    public DataProviderBase getDataProvider() {
        return dataProvider_Unicom_Lte;
    }

    @Override
    public GroupService getGroupService() {

        return groupServiceImpl_lte;
    }

    @Override
    public ElementTopology getElementTopologyr() {
        return null;
    }

    @Override
    public AlarmLibrary getAlarmLibrary() {
        return null;
    }

    @Override
    public AlarmLibrary getSpecifiedLibrary() {
        return null;
    }

    @Override
    public AlarmLibrary updateSpecifiedLibrary() {
        return null;
    }

    @Override
    public NodeService getNodeService() {

        return nodeServiceImpl_lte;
    }

    @Override
    public CellService getCellService() {

        return cellServiceImpl_lte;
    }
}
