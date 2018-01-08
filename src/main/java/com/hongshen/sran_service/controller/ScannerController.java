package com.hongshen.sran_service.controller;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.common.BaseController;
import com.hongshen.sran_service.service.impl.CacheService_Unicom_Lte;
import com.hongshen.sran_service.service.impl.CacheService_Unicom_Wcdma;
import com.hongshen.sran_service.service.impl.ScannerService_Unicom_Lte;
import com.hongshen.sran_service.service.impl.ScannerService_Unicom_Wcdma;
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
    private ScannerService_Unicom_Wcdma scannerServiceWcdma;

    @Autowired
    private ScannerService_Unicom_Lte scannerServiceLte;

    @Autowired
    private CacheService_Unicom_Wcdma caCheServiceWcdma;

    @Autowired
    private CacheService_Unicom_Lte caCheServiceLte;

    @Autowired
    private  NoticeWSController noticeWS;

    private static final Logger logger = LoggerFactory.getLogger(ScannerController.class);

    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/quotas/calculation")
    @Produces(MediaType.APPLICATION_JSON)
    public void calculationOld(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                              @HeaderParam("time")String time) throws IOException {

        String ret = null;

        if (time != null) {

            caCheServiceWcdma.setUpdateTimeForQuotaData(time);

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

    @POST
    @Path("/suppliers/{supplier}/quotas/calculation")
    @Produces(MediaType.APPLICATION_JSON)
    public void calculactionPost(JSONObject timeParams) throws IOException {

        timeParams.size();

        if (timeParams.getString("counterWcdmatime") != null &&
                !"".equals(timeParams.getString("counterWcdmatime"))) {

        } else if (timeParams.getString("counterLtetime") != null &&
                !"".equals(timeParams.getString("counterLtetime"))) {

        }
    }

    @GET
    @Path("/suppliers/{supplier}/quotas/calculation")
    @Produces(MediaType.APPLICATION_JSON)
    public void calculation(@PathParam("supplier")String supplier,
                            @HeaderParam("counterWcdmatime")String counterWcdmatime,
                            @HeaderParam("counterLtetime")String counterLtetime) throws IOException {

        System.out.println("************************** SRAN counterHistory 计算开始 **************************");

        String ret = null;

        /*String counterWcdmatime = "2017-12-06 10:00:00";
        String counterLtetime = "2017-12-08 10:00:00";*/

        if (counterWcdmatime != null) {

            caCheServiceWcdma.setUpdateTimeForQuotaData(counterWcdmatime);

            scannerServiceWcdma.cellCalculation(counterWcdmatime);

            JSONObject params = scannerServiceWcdma.nodeCalculation(counterWcdmatime);

            if (params != null){

                ret = scannerServiceWcdma.groupCalculation(params, counterWcdmatime);
            }

            JSONObject param = new JSONObject();

            param.put("supplier", supplier);
            param.put("generation", "wcdma");
            param.put("message", "The counterHistoryWcdma cell/node/group complete of the "
                    + counterWcdmatime + "calculation");
            param.put("time", counterWcdmatime);

            String path = Constants.SCANNER_SEND_WCDMA;

            ScannerHelper.httpclientCounterCalculation(path, param);
        }

        if (counterLtetime != null) {

            caCheServiceLte.setUpdateTimeForQuotaData(counterLtetime);

            scannerServiceLte.cellCalculation(counterLtetime);

            JSONObject params = scannerServiceLte.nodeCalculation(counterLtetime);

            if (params != null){

                ret = scannerServiceLte.groupCalculation(params, counterLtetime);
            }

            JSONObject param = new JSONObject();

            param.put("supplier", supplier);
            param.put("generation", "wcdma");
            param.put("message", "The counterHistoryLte cell/node/group complete of the "
                    + counterLtetime + "calculation");
            param.put("time", counterLtetime);

            String path = Constants.SCANNER_SEND_LTE;

            ScannerHelper.httpclientCounterCalculation(path, param);
        }
    }

    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/send")
    @Produces(MediaType.APPLICATION_JSON)
    public void send(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                     @HeaderParam("message")String message, @HeaderParam("time")String time) {

        JSONObject param = new JSONObject();

        param.put("supplier", supplier);
        param.put("generation", generation);
        param.put("message", message);
        param.put("time", time);

        noticeWS.sendAll(param);
    }

    /*@GET
    @Path("/suppliers/{supplier}/generations/{generation}/send")
    @Produces(MediaType.APPLICATION_JSON)
    public void srnd(@PathParam("supplier")String supplier, @PathParam("generation")String generation, JSONObject param) {

        noticeWS.sendAll(param);
    }*/
}
