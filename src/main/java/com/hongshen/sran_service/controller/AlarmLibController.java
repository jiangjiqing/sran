package com.hongshen.sran_service.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.common.BaseController;
import com.hongshen.sran_service.service.util.Constants;
import com.hongshen.sran_service.service.util.NetObjBase;
import com.hongshen.sran_service.service.util.NetObjFactory;
import com.hongshen.sran_service.service.util.QuotaHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

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

        String msg = "";
        List<JSONObject> alarmList = null;
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        if (obj == null) {
            msg += "Supplier or Generation has error.";

        }else{
            alarmList = obj.getAlarmLibService().getAlarmList();

            if (alarmList == null || alarmList.isEmpty() ||alarmList.size() == 0) {
                msg += "Get alarmList failed.";
            }else{
                for (JSONObject al : alarmList){
                    if (al.getString("alarmLevelId").length() == 0){
                        al.put("alarmLevelId", Constants.INVALID_ALARM_LEVEL);
                    }else {
                        al.put("alarmLevelId", al.getIntValue("alarmLevelId"));
                    }
                }
            }
        }
        if (msg.length() == 0){
            result.put("result", Constants.SUCCESS);
            result.put("data", alarmList);

        }else{
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA + msg);
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

        String msg ="";
        JSONObject alarm = null;
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier,generation);
        if(obj == null) {
            msg += "Supplier or Generation has error.";
        }else {
            if(alarmName == null || alarmName.isEmpty() || alarmName.length() == 0) {
                msg +="Alarm is null";
            }else {
                alarm = obj.getAlarmLibService().getAlarmByName(alarmName);

                if (alarm == null || alarm.isEmpty() || alarm.size() == 0) {
//                result.put("result", Constants.FAIL);
//                result.put("msg", Constants.MSG_NO_DATA);
                    msg += "Alarm is null.";
                }else{
                    if (alarm.getString("alarmLevelId").length() == 0){
                        alarm.put("alarmLevelId", Constants.INVALID_ALARM_LEVEL);
                    }else {
                        alarm.put("alarmLevelId", alarm.getIntValue("alarmLevelId"));
                    }
                }
            }
        }
        if (msg.length() == 0){

            result.put("result", Constants.SUCCESS);
            result.put("data", alarm);

        }else{

            result.put("result", Constants.FAIL);

            result.put("msg", Constants.MSG_NO_DATA + msg);

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
        String msg ="";
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier,generation);
        if (obj == null){
            msg += "Supplier or Generation has error.";
        }else {
            if (alarmName ==null || alarmName.isEmpty() || alarmName.length() == 0){
                msg +="AlarmName is null.";
            }else {
                if(param == null || param.size() == 0 || param.isEmpty()){
                    msg += "Param is null.";
                }else {
                    try {
                        int i = obj.getAlarmLibService().updateAlarmByName(alarmName, param);
                        if (i <= 0) {
                            msg +="Update failed.";

                        }
                    }catch (Exception e){
                        msg += "Parameters has error:" + e.getMessage();
                    }
                }
            }
        }
        if (msg.length() == 0){

            result.put("result", Constants.SUCCESS);
            result.put("data", Constants.MSG_UPDATE_OK);

        }else{
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_UPDATE_FAILED + msg);

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
        if(obj == null){
            msg += "Supplier or Generation has error.";
        }else {
            if (param == null || param.isEmpty() || param.size() == 0){
                msg += "Param is null.";
            }else {
                try {
                    if (param.getString("alarmNameId") != "" || param.getString("alarmNameId").length() == 0 || param.getString("alarmNameId").isEmpty()) {
                        int i = obj.getAlarmLibService().addAlarmIndex(param);
                        if (i == 0) {
                            msg = "alarmNameId is exist.";
                        }
                    }
                    if (msg.length() == 0 && param.getString("alarmName") != "") {
                        int i = obj.getAlarmLibService().addAlarm(param);
                        if (i == 0) {
                            msg = "alarmName is exist.";
                        }
                    }
                }catch (Exception e){
                    msg += "Parameters has error:" + e.getMessage();
                }
            }
        }

        if (msg.length() == 0){
            result.put("result", Constants.SUCCESS);
            result.put("msg", Constants.MSG_ADD_OK);

        } else {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_ADD_FAILED + msg);

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
        String msg = "";
        NetObjBase obj = objFactory.getNetObj(supplier,generation);
        if (obj == null){
            msg += "Supplier or Generation has error.";
        }else {
            try {
                int ret = obj.getAlarmLibService().deleteAlarmByName(alarmName);

                if (ret <= 0) {
                    msg +="deleteAlarm is Failed";
                }
            }catch (Exception e) {
                msg += "Parameters has error:" + e.getMessage();
            }
        }
        if (msg.length() == 0){
            result.put("result", Constants.SUCCESS);
            result.put("msg", Constants.MSG_DELETE_OK);
        } else {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_DELETE_FAILED + msg);
        }

        return result;
    }

    @PUT
    @Path("/suppliers/{supplier}/generations/{generation}/alarms/upload")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject protectImport(@RequestParam(value = "importJson") JSONObject importJson,
                                    @PathParam("supplier") String supplier,
                                    @PathParam("generation") String generation,
                                    @HeaderParam("Auth-Token") String authToken,
                                    @HeaderParam("loginName") String loginName) {
        JSONObject result = new JSONObject();
        String msg = "";
        int addnum = 0;
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        if (obj == null){
            msg +="Supplier or Generation has error.";
        }else {
            List<JSONObject> alarmList = obj.getAlarmService().getAlarmByName(importJson.getString("alarmNameId"),importJson.getString("alarmName"));
            if (alarmList.size() ==0 || alarmList.isEmpty() || alarmList == null){
                msg +="AlarmNameId or AlarmName is not Exist.";
            }else {
                try {
                    addnum = obj.getAlarmLibService().addAlarm(importJson);
                }catch (Exception e){
                    msg += "Parameter is Error.";
                }
                if (addnum < 0 ){
                    msg +="AddAlarm is Failed.";
                }
            }
        }

        if (msg.length() != 0) {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA + msg);

        } else {
            result.put("result", Constants.SUCCESS);
            result.put("msg", Constants.MSG_ADD_OK+"(Real/Total:" + addnum + ")");
        }
        return result;
    }
}
