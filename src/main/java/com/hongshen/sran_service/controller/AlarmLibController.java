package com.hongshen.sran_service.controller;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.common.BaseController;
import com.hongshen.sran_service.service.util.Constants;
import com.hongshen.sran_service.service.util.NetObjBase;
import com.hongshen.sran_service.service.util.NetObjFactory;
import com.hongshen.sran_service.service.util.QuotaHelper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

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
        //String e = "(EUtranCellRelation.pmHoPrepAttLteInterF+EUtranCellRelation.pmHoPrepAttLteIntraF)==0?100:100*(EUtranCellRelation.pmHoExeSuccLteInterF+EUtranCellRelation.pmHoExeSuccLteIntraF)/(EUtranCellRelation.pmHoPrepAttLteInterF+EUtranCellRelation.pmHoPrepAttLteIntraF)";
        String e = "(pmErab_LevSa.mp)*5 == -0?0:100*((1-(pmCellDowntimeAuto)/((pmErabLevSamp)*5))) ";
        int l = e.length();
        if (QuotaHelper.checkExpression(e)){
            String c = QuotaHelper.convertExpression(e);
        }
//        NetObjBase obj = objFactory.getNetObj(supplier, generation);
//        List<JSONObject> alarmList = obj.getAlarmLibService().getAlarmList();
//
//        if (alarmList == null || alarmList.isEmpty()){
//            result.put("result", Constants.FAIL);
//            result.put("msg", Constants.MSG_NO_DATA);
//
//        } else {
//            result.put("result", Constants.SUCCESS);
//            result.put("data", alarmList);
//        }

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

        if (alarm == null || alarm.isEmpty()){
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA);

        } else {
            result.put("result", Constants.SUCCESS);
            result.put("data", alarm);
        }

        return result;
    }

    // Update specified alarm lib
    @PUT
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
    @POST
    @Path("/suppliers/{supplier}/generations/{generation}/alarms")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject addAlarmInfo(@PathParam("supplier")String supplier,
                                   @PathParam("generation")String generation,
                                   @HeaderParam("Auth-Token")String authToken,
                                   JSONObject param){

        JSONObject result = new JSONObject();
        String msg = "";
        NetObjBase obj = objFactory.getNetObj(supplier,generation);

        if (param.getString("alarmNameId") != ""){
            int i = obj.getAlarmLibService().addAlarmIndex(param);
            if (i == 0){
                msg = "alarmNameId is exist.";
            }
        }
        if (msg.length() == 0 && param.getString("alarmName") != "") {
            int i = obj.getAlarmLibService().addAlarm(param);
            if (i == 0){
                msg = "alarmName is exist.";
            }
        }

        if (msg.length() == 0){
            result.put("result", Constants.SUCCESS);
            result.put("msg", Constants.MSG_ADD_OK);

        } else {
            result.put("result", Constants.FAIL);
            result.put("msg", msg);

        }

        return result;
    }

    // Delete specified alarm
    @DELETE
    @Path("/suppliers/{supplier}/generations/{generation}/alarms/{alarmName}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject addAlarmInfo(@PathParam("supplier")String supplier,
                                   @PathParam("generation")String generation,
                                   @PathParam("alarmName")String alarmName,
                                   @HeaderParam("Auth-Token")String authToken) {

        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier,generation);
        int ret = obj.getAlarmLibService().deleteAlarmByName(alarmName);

        if (ret > 0){
            result.put("result", Constants.SUCCESS);
            result.put("msg", Constants.MSG_DELETE_OK);

        } else {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_DELETE_FAILED);

        }

        return result;
    }
}
