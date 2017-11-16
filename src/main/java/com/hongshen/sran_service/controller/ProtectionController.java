package com.hongshen.sran_service.controller;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.common.BaseController;
import com.hongshen.sran_service.service.AlarmLibService;
import com.hongshen.sran_service.service.DataProviderBase;
import com.hongshen.sran_service.service.ElementInfoService;
import com.hongshen.sran_service.service.util.Constants;
import com.hongshen.sran_service.service.util.Httpclient;
import com.hongshen.sran_service.service.util.NetObjBase;
import com.hongshen.sran_service.service.util.NetObjFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
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
	
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/protections")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getProtectionList(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                   @HeaderParam("Auth-Token")String authToken) {

        JSONObject result = new JSONObject();
		
        String url = Constants.PATH_DUMMY;
        String method = Constants.METHOD_GET;

//      if (check(url, method, authToken)) {

        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        List<JSONObject> protectList = obj.getElementInfoService().getProtectList();

        if (!protectList.isEmpty()) {

            result.put("data", protectList);
            result.put("result", Constants.SUCCESS);
        } else {
            result.put("msg", Constants.MSG_NO_DATA);
            result.put("result", Constants.FAIL);
        }

        return result;
//        } else {
//			  result.put("result", Constants.FAIL);
//			  result.put("msg", Constants.MSG_NO_PERMISSION);
//            return result;
//        }

    }
}
