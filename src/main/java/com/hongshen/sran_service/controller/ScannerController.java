package com.hongshen.sran_service.controller;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.common.BaseController;
import com.hongshen.sran_service.service.util.NetObjBase;
import com.hongshen.sran_service.service.util.NetObjFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/sran/service/net/scanner")
public class ScannerController extends BaseController {

    @Autowired
    private NetObjFactory objFactory;

    private static final Logger logger = LoggerFactory.getLogger(ScannerController.class);

    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/quotas/{time}/calculation")
    @Produces(MediaType.APPLICATION_JSON)
    public String calculation(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                              @PathParam("time")String time) {

        String ret = null;

        if (time == null) {

            return ret;
        }

        NetObjBase netObj = objFactory.getNetObj(supplier, generation);

        //ret = netObj.getScannerService().cellCalculation(time);

        JSONObject params = netObj.getScannerService().nodeCalculation(time);

        ret = netObj.getScannerService().groupCalculation(params, time);

        return ret;
    }
}
