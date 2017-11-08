package com.hongshen.sran_service.service.util.impl;

import com.hongshen.sran_service.service.DataProviderBase;
import com.hongshen.sran_service.service.GroupService;
import com.hongshen.sran_service.service.impl.DataProvider_Unicom_Lte;
import com.hongshen.sran_service.service.impl.GroupServiceImpl_Lte;
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

    @Override
    public DataProviderBase getDataProvider() {
        return dataProvider_Unicom_Lte;
    }

    @Override
    public GroupService getGroupService() {

        return groupServiceImpl_lte;
    }
}
