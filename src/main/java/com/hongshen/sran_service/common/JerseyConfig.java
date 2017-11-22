package com.hongshen.sran_service.common;

import com.hongshen.sran_service.controller.*;
import org.glassfish.jersey.server.ResourceConfig;

public class JerseyConfig extends ResourceConfig{

    public JerseyConfig(){
        register(AlarmController.class);
        register(AlarmLibController.class);
        register(AuthorityController.class);
        register(ElementController.class);
        register(FavoriteContrller.class);
        register(MapController.class);
        register(ProtectionController.class);
        register(QuotaController.class);
        register(QuotaFormulaController.class);
        register(QuotaHistoryController.class);
        register(QuotaThresholdController.class);
        register(TaskController.class);
        register(TopologyController.class);
        register(UserController.class);
        register(CorsFilter.class);
    }
}
