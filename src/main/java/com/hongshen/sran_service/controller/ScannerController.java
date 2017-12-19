package com.hongshen.sran_service.controller;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.common.BaseController;
import com.hongshen.sran_service.service.impl.CacheService_Unicom_Wcdma;
import com.hongshen.sran_service.service.util.Constants;
import com.hongshen.sran_service.service.util.NetObjBase;
import com.hongshen.sran_service.service.util.NetObjFactory;

import com.hongshen.sran_service.service.util.ScannerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("/sran/service/net/scanner")
public class ScannerController extends BaseController {

    @Autowired
    private NetObjFactory objFactory;

    @Autowired
    private CacheService_Unicom_Wcdma caCheService;

    @Autowired
    private  NoticeWSController noticeWS;

    private static final Logger logger = LoggerFactory.getLogger(ScannerController.class);

    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/quotas/calculation")
    @Produces(MediaType.APPLICATION_JSON)
    public void calculation(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                              @HeaderParam("time")String time) throws IOException {

        String ret = null;

        if (time != null) {

            caCheService.setUpdateTimeForQuotaData(time);

            NetObjBase netObj = objFactory.getNetObj(supplier, generation);

            ret = netObj.getScannerService().cellCalculation(time);

            JSONObject params = netObj.getScannerService().nodeCalculation(time);

            if (params != null){

                ret = netObj.getScannerService().groupCalculation(params, time);
            }

            String path = Constants.SCANNER_SEND_WCDMA;

            ScannerHelper.httpclient(path);

        }


    }
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/send")
    @Produces(MediaType.APPLICATION_JSON)
    public void srnd(@PathParam("supplier")String supplier, @PathParam("generation")String generation, JSONObject param) {
        noticeWS.sendAll(param);
    }

}
