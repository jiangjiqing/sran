package com.hongshen.sran_service.service.util;

import com.hongshen.sran_service.service.AlarmLibrary;
import com.hongshen.sran_service.service.DataProviderBase;
import com.hongshen.sran_service.service.ElementTopology;
import com.hongshen.sran_service.service.GroupService;

/**
 * Created by poplar on 17-10-30.
 */

public interface NetObjBase {

    DataProviderBase getDataProvider();

    GroupService getGroupService();

    ElementTopology getElementTopologyr();

    AlarmLibrary getAlarmLibrary();

    AlarmLibrary getSpecifiedLibrary();


    AlarmLibrary updateSpecifiedLibrary();
}
