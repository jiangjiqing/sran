package com.hongshen.sran_service.controller;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.service.util.Constants;
import com.hongshen.sran_service.service.util.NetObjBase;
import com.hongshen.sran_service.service.util.NetObjFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by poplar on 11/13/17.
 */
@Path("/sran/service/net/formula")
public class QuotaFormulaController {

    @Autowired
    private NetObjFactory objFactory;

    // Query formula list
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/quotas")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getQuotaList(@PathParam("supplier")String supplier,
                                   @PathParam("generation")String generation,
                                   @HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();

        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        List<JSONObject> quotaList = obj.getCacheService().getFormulaList(true);

        if (quotaList.isEmpty()) {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA);

        } else {
            result.put("result", Constants.SUCCESS);
            result.put("data", quotaList);
        }

        return result;
    }

    // Update quota info
    @PUT
    @Path("/suppliers/{supplier}/generations/{generation}/nets/quotas/{quotaName}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject modifyQuotaInfo(@PathParam("supplier")String supplier,
                                      @PathParam("generation")String generation,
                                      @HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();
        //TODO
        return result;
    }

    // Delete quota info
    @DELETE
    @Path("/suppliers/{supplier}/generations/{generation}/nets/quotas/{quotaName}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject deleteQuotaInfo(@PathParam("supplier")String supplier,
                                      @PathParam("generation")String generation,
                                      @HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();
        //TODO
        return result;
    }

    // Query counter list
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/counters/list")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getCounterList(@PathParam("supplier")String supplier,
                                     @PathParam("generation")String generation,
                                     @HeaderParam("Auth-Token")String authToken){
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        List<JSONObject> counterList = obj.getCacheService().getCounterList(true);

        if (counterList.isEmpty()) {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA);

        } else {
            result.put("result", Constants.SUCCESS);
            result.put("data", counterList);
        }

        return result;
    }
}
