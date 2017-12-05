package com.hongshen.sran_service.controller;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.common.BaseController;
import com.hongshen.sran_service.service.TelecomRoomService;
import com.hongshen.sran_service.service.util.Constants;
import com.hongshen.sran_service.service.util.NetObjBase;
import com.hongshen.sran_service.service.util.NetObjFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * Created by poplar on 11/14/17.
 */
@Path("/sran/service/net/mapinfo")
public class TopologyController extends BaseController {
//    @Autowired
//    private NetObjFactory objFactory;
//    //    Network element topology
//    @GET
//    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/roomlist")
//    @Produces(MediaType.APPLICATION_JSON)
//    public JSONObject getElementTopology(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
//                                         @HeaderParam("Auth-Token")String authToken){
//
//        JSONObject result = new JSONObject();
//        String url = Constants.ZB_ELEMENT;
//        String method = Constants.METHOD_GET;
//
//        if (check(url, method, authToken)) {
//
//            NetObjBase obj = objFactory.getNetObj(supplier,generation);
//            TelecomRoomService telecomRoomService = obj.getElementTopologyr();
//            Map<String,Object> GroupWcdma =telecomRoomService.getElementTopologyr();
//
//            if (!GroupWcdma.isEmpty()){
//
//                result.put("data", GroupWcdma);
//                result.put("result", Constants.SUCCESS);
//            } else {
//
//                result.put("result", Constants.FAIL);
//            }
//
//            return result;
//        } else {
//
//            return result;
//        }
//    }
}
