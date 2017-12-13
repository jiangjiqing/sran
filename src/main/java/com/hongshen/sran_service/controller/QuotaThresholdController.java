package com.hongshen.sran_service.controller;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.common.BaseController;
import com.hongshen.sran_service.service.util.Constants;
import com.hongshen.sran_service.service.util.NetObjBase;
import com.hongshen.sran_service.service.util.NetObjFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by poplar on 11/13/17.
 */
@Path("/sran/service/net/threshold")
public class QuotaThresholdController extends BaseController{

    @Autowired
    private NetObjFactory objFactory;

    // Query thresholds
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/{level}/thresholds")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getGroupList(@PathParam("supplier")String supplier,
                                   @PathParam("generation")String generation,
                                   @PathParam("level")String level,
                                   @HeaderParam("Auth-Token")String authToken) {

        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        List<JSONObject> dataList = new ArrayList<>();

        switch (level)
        {
            case "groups":
                dataList = obj.getCacheService().getThresholdGroupList();
                break;

            case "nodes":
                dataList = obj.getCacheService().getThresholdNodeList();
                break;

            case "cells":
                dataList = obj.getCacheService().getThresholdCellList();
                break;

            default :
                dataList = null;
                break;
        }

        if(dataList == null || dataList.isEmpty()){
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA);

        }else{
            result.put("result", Constants.SUCCESS);
            result.put("data", dataList);
        }

        return result;
    }

    // Update thresholds
    @PUT
    @Path("/suppliers/{supplier}/generations/{generation}/nets/{level}/quotas/{quotaName}/thresholds")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getGroupList(@RequestParam(value = "quotaThres") JSONObject quotaThres,
                                   @PathParam("supplier")String supplier,
                                   @PathParam("generation")String generation,
                                   @PathParam("level")String level,
                                   @PathParam("quotaName")String quotaName,
                                   @HeaderParam("Auth-Token")String authToken) {

        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        quotaThres.put("quotaName",quotaName);

        Integer num = 0;

        switch (level)
        {
            case "groups":
                num = obj.getQuotaService().setGroupThreshold(quotaThres);
                obj.getCacheService().resetThresholdGroupList();
                break;

            case "nodes":
                num = obj.getQuotaService().setNodeThreshold(quotaThres);
                obj.getCacheService().resetThresholdNodeList();
                break;

            case "cells":
                num = obj.getQuotaService().setCellThreshold(quotaThres);
                obj.getCacheService().resetThresholdCellList();
                break;

            default :
                num = 0;
                break;
        }

        if(num == 0){
            result.put("result",Constants.FAIL);
            result.put("msg", Constants.MSG_UPDATE_FAILED);

        }else{
            result.put("result",Constants.SUCCESS);
            result.put("msg", Constants.MSG_UPDATE_OK);
        }

        return result;
    }
}
