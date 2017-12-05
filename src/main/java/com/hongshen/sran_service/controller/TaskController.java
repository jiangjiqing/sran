package com.hongshen.sran_service.controller;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.common.BaseController;
import com.hongshen.sran_service.service.TaskService;
import com.hongshen.sran_service.service.util.Constants;
import com.hongshen.sran_service.service.util.NetObjBase;
import com.hongshen.sran_service.service.util.NetObjFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * Created by poplar on 11/13/17.
 */
@Path("/sran/service/net/task")
public class TaskController extends BaseController {
//    @Autowired
//    private NetObjFactory objFactory;
//
//
////    Query group 3G instruction list
//    @GET
//    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups")
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
//            TaskService taskService = obj.getTaskInfo();
//            Map<String,Object> taskInfo =taskService.getTaskInfo();
//
//            if (!taskInfo.isEmpty()){
//
//                result.put("data", taskInfo);
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
