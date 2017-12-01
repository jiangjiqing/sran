package com.hongshen.sran_service.controller;

/**
 * Created by poplar on 11/13/17.
 */
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

@Path("/sran/service/net/threshold")
public class QuotaThresholdController extends BaseController{
    @Autowired
    private NetObjFactory objFactory;
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/{level}/thresholds")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getGroupList(@PathParam("supplier")String supplier,
                                   @PathParam("generation")String generation,
                                   @PathParam("level")String level,
                                   @HeaderParam("Auth-Token")String authToken) {
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        JSONObject result = new JSONObject();
        List<JSONObject> list = new ArrayList<>();

        switch (level)
        {
            case "groups":
                list = obj.getCacheService().getThresholdGroupList();
                break;

            case "nodes":
                list = obj.getCacheService().getThresholdNodeList();
                break;

            case "cells":
                list = obj.getCacheService().getThresholdCellList();
                break;

            default :
                list = null;
                break;
        }
        if(list.isEmpty()){
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA);
        }else{
            result.put("result", Constants.SUCCESS);
            result.put("data",list);
        }
        return result;
    }

    @PUT
    @Path("/suppliers/{supplier}/generations/{generation}/nets/{level}/quotas/{quotaName}/thresholds")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getGroupList(@RequestParam(value = "quotaThres") JSONObject quotaThres,
                                   @PathParam("supplier")String supplier,
                                   @PathParam("generation")String generation,
                                   @PathParam("level")String level,
                                   @PathParam("quotaName")String quotaName,
                                   @HeaderParam("Auth-Token")String authToken) {
        quotaThres.put("quotaName",quotaName);
      //  System.out.println(quotaThres);
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        JSONObject result = new JSONObject();
        Integer Num = 0;
        switch (level)
        {
            case "groups":
                 Num = obj.getQuotaService().setGroup(quotaThres);
                 obj.getCacheService().resetThresholdGroupList();
                break;

            case "nodes":
                 Num = obj.getQuotaService().setNode(quotaThres);
                 obj.getCacheService().resetThresholdNodeList();
                break;

            case "cells":
                 Num = obj.getQuotaService().setCell(quotaThres);
                 obj.getCacheService().resetThresholdCellList();
                break;

            default :
                Num = 0;
        }
            if(Num == 0){
                result.put("result",Constants.FAIL);
                result.put("msg", Constants.MSG_NO_DATA);
            }else{
                result.put("result",Constants.SUCCESS);
            }
        return result;
    }
}
