package com.hongshen.sran_service.common;

import com.hongshen.sran_service.controller.NetElementQuotaController;
import org.glassfish.jersey.server.ResourceConfig;

public class JerseyConfig extends ResourceConfig{

    public JerseyConfig(){

        register(NetElementQuotaController.class);
    }
}
