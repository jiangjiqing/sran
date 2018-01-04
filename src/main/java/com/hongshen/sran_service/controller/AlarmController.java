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
@Path("/sran/service/net/alarm")
public class AlarmController {
	
    @Autowired
    private NetObjFactory objFactory;

    // query all alarm log
    @GET
    @Path("/suppliers/{supplier}/nets/alarms")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getAllAlarms (@PathParam("supplier")String supplier,
                                    @HeaderParam("Auth-Token")String authToken) {

        String msg = "";
		JSONObject result = new JSONObject();

        List<JSONObject> dataList = new ArrayList<JSONObject>();
        NetObjBase objWcdma = objFactory.getNetObj(supplier, Constants.WCDMA);
        NetObjBase objLte = objFactory.getNetObj(supplier, Constants.LTE);

        if (objWcdma == null || objLte == null){
            msg += "Supplier has error.";

        }else {
            List level = new ArrayList();
            level.add("1");
            level.add("2");
//            level.add("3");
            for (int i = 0;i<level.size();i++){
                System.out.println(level.get(i));
                List<JSONObject> AlarmWcdmaInfoList = objWcdma.getAlarmService().getAlarmInfoList1((String) level.get(i));
                List<JSONObject> AlarmLteInfoList = objLte.getAlarmService().getAlarmInfoList1((String) level.get(i));
                for (int j = 0 ; j<AlarmWcdmaInfoList.size();j++){
                    System.out.println(AlarmWcdmaInfoList.get(j));
                    AlarmWcdmaInfoList.get(j).put("generation",Constants.WCDMA);
                }
                for (int j = 0 ; j<AlarmLteInfoList.size();j++){
                    AlarmLteInfoList.get(j).put("generation",Constants.LTE);
                }
                dataList.addAll(AlarmWcdmaInfoList);
                dataList.addAll(AlarmLteInfoList);
            }


        }
        if (msg.length() == 0 && dataList != null && dataList.size() != 0){
            result.put("result", Constants.SUCCESS);
            result.put("data", dataList);

        }else {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA + msg);
        }
        return result;
    }
//     @GET
//    @Path("/suppliers/{supplier}/nets/alarms")
//    @Produces(MediaType.APPLICATION_JSON)
//    public JSONObject getAllAlarmss (@PathParam("supplier")String supplier,
//                                    @HeaderParam("Auth-Token")String authToken) {
//
//        String msg = "";
//		JSONObject result = new JSONObject();
//        List<JSONObject> dataList = new ArrayList<JSONObject>();

//        NetObjBase objWcdma = objFactory.getNetObj(supplier, Constants.WCDMA);
//        NetObjBase objLte = objFactory.getNetObj(supplier, Constants.LTE);
//
//        if (objWcdma == null || objLte == null){
//            msg += "Supplier has error.";
//
//        }else {
//            List<JSONObject> protectListWcdma = objWcdma.getAlarmService().getProtectAlarmInfoList();
//            List<JSONObject> normalListWcdma = objWcdma.getAlarmService().getNormalAlarmInfoList();
//            List<JSONObject> protectListLte = objLte.getAlarmService().getProtectAlarmInfoList();
//            List<JSONObject> normalListLte = objLte.getAlarmService().getNormalAlarmInfoList();
//
//            // add protect alarm
//            if ((protectListWcdma != null && protectListLte != null) &&
//                    (!protectListWcdma.isEmpty() || !protectListLte.isEmpty())) {
//
//                JSONObject protect = new JSONObject();
//                protect.put("type", "protect");
//
//                List<JSONObject> protectList = new ArrayList<JSONObject>();
//
//                // add 3G
//                if (!protectListWcdma.isEmpty()) {
//                    JSONObject dataWcdma = new JSONObject();
//
//                    dataWcdma.put("generation", Constants.WCDMA);
//                    dataWcdma.put("alarms", protectListWcdma);
//                    protectList.add(dataWcdma);
//                }
//
//                // add 4G
//                if (!protectListLte.isEmpty()) {
//                    JSONObject dataLte = new JSONObject();
//
//                    dataLte.put("generation", Constants.LTE);
//                    dataLte.put("alarms", protectListLte);
//                    protectList.add(dataLte);
//                }
//
//                protect.put("list", protectList);
//                dataList.add(protect);
//            }
//
//            // add normal alarm
//            if ((normalListWcdma != null && normalListLte != null) &&
//                 (!normalListWcdma.isEmpty() || !normalListLte.isEmpty())) {
//
//                JSONObject normal = new JSONObject();
//                normal.put("type", "normal");
//
//                List<JSONObject> normalList = new ArrayList<JSONObject>();
//
//                // add 3G
//                if (!normalListWcdma.isEmpty()) {
//                    JSONObject dataWcdma = new JSONObject();
//
//                    dataWcdma.put("generation", Constants.WCDMA);
//                    dataWcdma.put("alarms", normalListWcdma);
//                    normalList.add(dataWcdma);
//                }
//
//                // add 4G
//                if (!normalListLte.isEmpty()) {
//                    JSONObject dataLte = new JSONObject();
//
//                    dataLte.put("generation", Constants.LTE);
//                    dataLte.put("alarms", normalListLte);
//                    normalList.add(dataLte);
//                }
//
//                normal.put("list", normalList);
//                dataList.add(normal);
//            }
//        }
//
//        if (msg.length() == 0 && dataList != null && dataList.size() != 0){
//            result.put("result", Constants.SUCCESS);
//            result.put("data", dataList);
//
//        }else {
//            result.put("result", Constants.FAIL);
//            result.put("msg", Constants.MSG_NO_DATA + msg);
//        }

//        return result;
//    }

    // query group alarm log 
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupname}/alarms")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getGroupAlarms(@PathParam("supplier")String supplier,
                                     @PathParam("generation")String generation,
                                     @PathParam("groupname")String groupname,
                                     @HeaderParam("Auth-Token")String authToken){

        String msg = "";
        JSONObject result = new JSONObject();
        List<JSONObject> dataList = new ArrayList<>();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        if (obj == null){
            msg += "Supplier or Generation has error.";

        }else {
            dataList = obj.getAlarmService().getGroupAlarmByName(groupname);
        }

        if (msg.length() == 0 && dataList != null && dataList.size() != 0){
            result.put("result", Constants.SUCCESS);
            result.put("data", dataList);

        } else {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA + msg);
        }

        return result;
    }

    // query node alarm log
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupname}/nodes/{nodename}/alarms")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getNodeAlarms(@PathParam("supplier")String supplier,
                                    @PathParam("generation")String generation,
                                    @PathParam("groupname")String groupName,
                                    @PathParam("nodename")String nodeName,
                                    @HeaderParam("Auth-Token")String authToken){

        String msg = "";
        JSONObject result = new JSONObject();
        List<JSONObject> dataList = new ArrayList<>();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        if (obj == null){
            msg += "Supplier or Generation has error.";

        }else {
            dataList = obj.getAlarmService().getNodeAlarmByName(nodeName);
        }

        if (msg.length() == 0 && dataList != null && dataList.size() != 0){
            result.put("result", Constants.SUCCESS);
            result.put("data", dataList);

        } else {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA + msg);
        }

        return result;
    }

    // query cell alarm log
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupname}/nodes/{nodename}/cells/{cellname}/alarms")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getCellAlarms(@PathParam("supplier")String supplier,
                                    @PathParam("generation")String generation,
                                    @PathParam("groupname")String groupName,
                                    @PathParam("nodename")String nodeName,
                                    @PathParam("cellname")String cellName,
                                    @HeaderParam("Auth-Token")String authToken){

        String msg = "";
        JSONObject result = new JSONObject();
        List<JSONObject> dataList = new ArrayList<>();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        if (obj == null){
            msg += "Supplier or Generation has error.";

        }else {
            dataList = obj.getAlarmService().getCellAlarmByName(cellName);
        }

        if (msg.length() == 0 && dataList != null && dataList.size() != 0){
            result.put("result", Constants.SUCCESS);
            result.put("data", dataList);

        } else {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA + msg);
        }

        return result;
    }
}
