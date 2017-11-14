package com.hongshen.sran_service.service.util;

import com.hongshen.sran_service.service.*;

/**
 * Created by poplar on 17-10-30.
 */

public interface NetObjBase {

    DataProviderBase getDataProvider();

    GroupService getGroupService();

    TelecomRoomService getElementTopologyr();

    AlarmLibService getAlarmLibrary();

    AlarmLibService getSpecifiedLibrary();


    AlarmLibService updateSpecifiedLibrary();

    NodeService getNodeService();

    CellService getCellService();

    AlarmLibService addSpecifiedLibrary();
}
