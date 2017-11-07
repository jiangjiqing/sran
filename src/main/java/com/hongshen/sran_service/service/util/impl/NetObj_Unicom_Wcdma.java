package com.hongshen.sran_service.service.util.impl;

import com.hongshen.sran_service.service.DataProviderBase;
import com.hongshen.sran_service.service.impl.DataProvider_Unicom_Wcdma;
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
    @Override
    public DataProviderBase getDataProvider() {
        return dataProvider_Unicom_Wcdma;
    }
}
