package com.hongshen.sran_service.controller;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.common.BaseController;
import com.hongshen.sran_service.service.DataProviderBase;
import com.hongshen.sran_service.service.util.Constants;
import com.hongshen.sran_service.service.util.Httpclient;
import com.hongshen.sran_service.service.util.NetObjBase;
import com.hongshen.sran_service.service.util.NetObjFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * Created by poplar on 11/13/17.
 */
@Path("/sran/service/net/protection")
public class ProtectionController extends BaseController{
    @Autowired
    private NetObjFactory objFactory;

    @Autowired
    private Httpclient httpclient;

    //Re guarantee network element
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/protectednets")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getZBElement(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                   @HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();
        String url = Constants.ZB_ELEMENT;
        String method = Constants.METHOD_GET;

        if (check(url, method, authToken)) {

            NetObjBase obj = objFactory.getNetObj(supplier,generation);
            DataProviderBase dataProvider = obj.getDataProvider();
            Map<String,Object> protect =dataProvider.getProtect();

            if (!protect.isEmpty()){

                result.put("data", protect);
                result.put("status", Constants.SUCCESS);
            } else {

                result.put("status", Constants.FAIL);
            }

            return result;
        } else {

            return result;
        }

    }
}
