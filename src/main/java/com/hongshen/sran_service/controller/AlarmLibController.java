package com.hongshen.sran_service.controller;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.common.BaseController;
import com.hongshen.sran_service.service.AlarmLibService;
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
@Path("/sran/service/alarmlib")
public class AlarmLibController extends BaseController{
//    @Autowired
//    private NetObjFactory objFactory;
//    //    Query all alarms
//    @GET
//    @Path("/suppliers/{supplier}/generations/{generation}/alarms")
//    @Produces(MediaType.APPLICATION_JSON)
//    public JSONObject getAlarmLibrary(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
//                                      @HeaderParam("Auth-Token")String authToken){
//
//        JSONObject result = new JSONObject();
//        String url = Constants.ZB_ELEMENT;
//        String method = Constants.METHOD_GET;
//
//        if (check(url, method, authToken)) {
//
//            NetObjBase obj = objFactory.getNetObj(supplier,generation);
//            AlarmLibService alarmLibService = obj.getAlarmLibrary();
//            Map<String,Object> alarm_Library =alarmLibService.getAlarmLibrary();
//
//            if (!alarm_Library.isEmpty()){
//
//                result.put("data", alarm_Library);
//                result.put("status", Constants.SUCCESS);
//            } else {
//
//                result.put("status", Constants.FAIL);
//            }
//
//            return result;
//        } else {
//
//            return result;
//        }
//    }
//    //    Query specified alarm
//    @GET
//    @Path("/suppliers/{supplier}/generations/{generation}/alarms/{alarmNameId}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public JSONObject getSpecifiedLibrary(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
//                                          @PathParam("alarmNameId")String alarmNameId,@HeaderParam("Auth-Token")String authToken){
//
//        JSONObject result = new JSONObject();
//        String url = Constants.ZB_ELEMENT;
//        String method = Constants.METHOD_GET;
//
//        if (check(url, method, authToken)) {
//
//            NetObjBase obj = objFactory.getNetObj(supplier,generation);
//            AlarmLibService alarmLibService = obj.getSpecifiedLibrary();
//            Map<String,Object> specifiedLibrary =alarmLibService.getSpecifiedLibrary(alarmNameId);
//
//            if (!specifiedLibrary.isEmpty()){
//
//                result.put("data", specifiedLibrary);
//                result.put("status", Constants.SUCCESS);
//            } else {
//
//                result.put("status", Constants.FAIL);
//            }
//
//            return result;
//        } else {
//
//            return result;
//        }
//    }
//    //update specified alarm
//    @POST
//    @Path("/suppliers/{supplier}/generations/{generation}/alarms/{alarmNameId}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public JSONObject updateSpecifiedLibrary(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
//                                             @PathParam("alarmNameId")String alarmNameId, @HeaderParam("Auth-Token")String authToken,
//                                             JSONObject param){
//
//        JSONObject result = new JSONObject();
//        String url = Constants.ZB_ELEMENT;
//        String method = Constants.METHOD_GET;
//        String name = param.getString("name");
//        if (check(url, method, authToken)) {
//
//            NetObjBase obj = objFactory.getNetObj(supplier,generation);
//            AlarmLibService alarmLibService = obj.updateSpecifiedLibrary();
//            alarmLibService.updateSpecifiedLibrary(alarmNameId,name);
//
////            if (!specifiedLibrary.isEmpty()){
////
////                result.put("data", specifiedLibrary);
////                result.put("status", Constants.SUCCESS);
////            } else {
////
////                result.put("status", Constants.FAIL);
////            }
//            result.put("status", Constants.SUCCESS);
//            return result;
//        } else {
//
//            return result;
//        }
//    }
//    //add specified alarm
//    @PUT
//    @Path("/suppliers/{supplier}/generations/{generation}/alarms")
//    @Produces(MediaType.APPLICATION_JSON)
//    public JSONObject addSpecifiedLibrary(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
//                                          @HeaderParam("Auth-Token")String authToken,JSONObject param){
//
//        JSONObject result = new JSONObject();
//        String url = Constants.ZB_ELEMENT;
//        String method = Constants.METHOD_GET;
//        String alarm_name_id = param.getString("alarm_name_id");
//        String alarm_name = param.getString("alarm_name");
//        String alarm_meaning = param.getString("alarm_meaning");
//        String alarm_level_id = param.getString("alarm_level_id");
//        String alarm_scope = param.getString("alarm_scope");
//        String recommend = param.getString("recommend");
//
//
////        if (check(url, method, authToken)) {
//
//        NetObjBase obj = objFactory.getNetObj(supplier,generation);
//        AlarmLibService alarmLibService = obj.addSpecifiedLibrary();
//        alarmLibService.addSpecifiedLibrary(alarm_name_id,alarm_name,alarm_meaning,alarm_level_id,alarm_scope,recommend);
//
////            if (!specifiedLibrary.isEmpty()){
////
////                result.put("data", specifiedLibrary);
////                result.put("status", Constants.SUCCESS);
////            } else {
////
////                result.put("status", Constants.FAIL);
////            }
//        result.put("status", Constants.SUCCESS);
////            return result;
////        } else {
//
//        return result;
////        }
//    }
}
