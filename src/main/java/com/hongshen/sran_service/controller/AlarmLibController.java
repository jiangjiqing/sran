package com.hongshen.sran_service.controller;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.common.BaseController;
import com.hongshen.sran_service.service.util.Constants;
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
@Path("/sran/service/alarmlib")
public class AlarmLibController extends BaseController{

    @Autowired
    private NetObjFactory objFactory;

    // Query alarm lib list
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/alarms")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getAlarmList(@PathParam("supplier")String supplier,
                                      @PathParam("generation")String generation,
                                      @HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        List<JSONObject> alarmList = obj.getAlarmLibService().getAlarmList();

        if (!alarmList.isEmpty()){
            result.put("result", Constants.SUCCESS);
            result.put("data", alarmList);

        } else {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA);
        }

        return result;
    }

    // Query specified alarm lib
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/alarms/{alarmName}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getAlarmInfo(@PathParam("supplier")String supplier,
                                               @PathParam("generation")String generation,
                                               @PathParam("alarmName")String alarmName,
                                               @HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier,generation);
        JSONObject alarm = obj.getAlarmLibService().getAlarmByName(alarmName);

        if (!alarm.isEmpty()){
            result.put("result", Constants.SUCCESS);
            result.put("data", alarm);

        } else {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA);
        }

        return result;
    }

    // Update specified alarm lib
    @POST
    @Path("/suppliers/{supplier}/generations/{generation}/alarms/{alarmName}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject updateAlarmInfo(@PathParam("supplier")String supplier,
                                                  @PathParam("generation")String generation,
                                                  @PathParam("alarmName")String alarmName,
                                                  @HeaderParam("Auth-Token")String authToken,
                                                  JSONObject param){

        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier,generation);
        int i = obj.getAlarmLibService().updateAlarmByName(alarmName,param);

        if (i > 0){
            result.put("result", Constants.SUCCESS);
            result.put("msg", Constants.MSG_UPDATE_OK);

        } else {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_UPDATE_FAILED);

        }
        return result;
    }

    // Add specified alarm
    @PUT
    @Path("/suppliers/{supplier}/generations/{generation}/alarms")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject addAlarmInfo(@PathParam("supplier")String supplier,
                                          @PathParam("generation")String generation,
                                          @HeaderParam("Auth-Token")String authToken,
                                          JSONObject param){

        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier,generation);
        int i = obj.getAlarmLibService().addAlarm(param);

        if (i > 0){
            result.put("result", Constants.SUCCESS);
            result.put("msg", Constants.MSG_ADD_OK);

        } else {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_ADD_FAILED);

        }

        return result;
    }
}
