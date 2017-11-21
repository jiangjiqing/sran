package com.hongshen.sran_service.common;

import com.hongshen.sran_service.controller.*;
import org.glassfish.jersey.server.ResourceConfig;

public class JerseyConfig extends ResourceConfig{

    public JerseyConfig(){
        register(NetElementQuotaController.class);
        register(AlarmController.class);
        register(AlarmLibController.class);
        register(TaskController.class);
        register(AuthorityController.class);
        register(ElementController.class);
        register(FavoriteContrller.class);
        register(MapController.class);
        register(ProtectionController.class);
        register(QuoatFormulaController.class);
        register(QuotaController.class);
        register(QuotaHistoryController.class);
        register(QuotaThresholdController.class);
        register(TopologyController.class);
        register(UserController.class);
        register(CorsFilter.class);
    }
}
