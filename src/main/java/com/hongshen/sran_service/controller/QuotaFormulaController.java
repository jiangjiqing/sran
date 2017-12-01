package com.hongshen.sran_service.controller;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.service.util.Constants;
import com.hongshen.sran_service.service.util.Httpclient;
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

    @Autowired
    private Httpclient httpclient;

    // query quota list
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/quotas")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getQuotaList(@PathParam("supplier")String supplier,
                                   @PathParam("generation")String generation,
                                   @HeaderParam("Auth-Token")String authToken){
        JSONObject result = new JSONObject();
        String url = Constants.PATH_DUMMY;
        String method = Constants.METHOD_GET;

//        if (check(url, method, authToken)) {

        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        List<JSONObject> quotaList = obj.getCacheService().getFormulaList(true);
        result.put("data", quotaList);
        result.put("result", Constants.SUCCESS);

        return result;
        //
//        } else {
//			  result.put("result", Constants.FAIL);
//			  result.put("msg", Constants.MSG_NO_PERMISSION);
//            return result;
//        }
    }

    // modify quota info
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

    // delete quota info
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

    // query counter list
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/counters/list")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getCounterList(@PathParam("supplier")String supplier,
                                     @PathParam("generation")String generation,
                                     @HeaderParam("Auth-Token")String authToken){
        JSONObject result = new JSONObject();
        //TODO
        return result;
    }
}
