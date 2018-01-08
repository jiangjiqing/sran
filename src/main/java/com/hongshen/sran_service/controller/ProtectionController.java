package com.hongshen.sran_service.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.common.BaseController;
import com.hongshen.sran_service.service.util.Constants;
import com.hongshen.sran_service.service.util.NetObjBase;
import com.hongshen.sran_service.service.util.NetObjFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.SQLErrorCodes;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by poplar on 11/13/17.
 */
@Path("/sran/service/net/protection")
public class ProtectionController extends BaseController{

    @Autowired
    private NetObjFactory objFactory;

    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/protections")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getProtectionList(@PathParam("supplier")String supplier,
                                        @PathParam("generation")String generation,
                                        @HeaderParam("Auth-Token")String authToken,
                                        @HeaderParam("loginName")String loginName) {

        String msg = "";
        JSONObject result = new JSONObject();
        List<JSONObject> dataList = new ArrayList<JSONObject>();
        List<JSONObject> authList = new ArrayList<>();

        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        if (obj == null){
            msg += "Supplier or Generation has error.";
        }else {
            List<JSONObject> protectList = obj.getElementInfoService().getProtectList();
            if (protectList == null || protectList.isEmpty()){
                msg +="getProtectList is Failed.";
            }else {
                for (JSONObject protect : protectList) {
                    String groupName = protect.getString("groupName");

                    authList = obj.getAuthorityService().getAuthorityByLoginNameList(loginName);
                    for (int i = 0;i<authList.size();i++) {
                        if (authList.get(i).getString("list").contains(groupName)) {
                            String nodeName = protect.getString("nodeName");

                            if (nodeName == null || nodeName.trim().length() <= 0) {
                                continue;

                            } else {
                                // alarm
                                List<JSONObject> alarmList = obj.getAlarmService().getNodeAlarmByName(nodeName);
                                protect.put("alarmList", alarmList);

                                if (alarmList.size() != 0) {
                                    protect.put("alarmStatus", true);

                                } else {
                                    protect.put("alarmStatus", false);
                                }

                                // level
                                JSONObject level = obj.getQuotaService().getNodeLevel(nodeName);

                                if (level != null && level.getIntValue("level") != -1) {
                                    protect.put("level", level.getIntValue("level"));

                                } else {
                                    protect.put("level", Constants.INVALID_VALUE_LEVEL);

                                }
                                dataList.add(protect);
                            }
                        }
                    }
                }
            }
        }

        if (dataList == null || dataList.isEmpty() || msg.length() != 0) {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA + msg);

        } else {
            result.put("result", Constants.SUCCESS);
            result.put("data", dataList);
        }

        return result;
    }

    //    @RequestMapping(value = "/v1/dc-map")
//    public Map test() {
//        boolean sign = false;
//        String result = "";
//        Map jsonResult = new HashMap();
//        Map<String, Object> data = new HashMap<String, Object>();
//        String authResult = null;
//        Map<String,Object> role2 = new HashMap<String,Object>();
//
//        String supplier = "unicom";
//        String generation  = "lte";
//        Role role = new Role();
//        Role role1 = null;
//        role.setRoleId(1);
//        int id=role.getRoleId();
//        //Shiro authorization
//        String checkUrl = "/service/v1/dc-map";
//        String checkMethod = "GET";
//        String url = Constants.SHIRO_URI+"?checkUrl="+checkUrl+"&checkMethod="+checkMethod;
//        String token = "eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYiLCJ0eXAiOiJKV1QifQ.eyJzdWIiOiJlY2FwIiwiaXNzIjoiRXJpY3Nzb24iLCJ1c2VybmFtZSI6InBldGVyIn0.IbXgDC975i4M4D3AVeeaWFLC3YD3zY9-6XiNbiocxNo";
//        try {
//            authResult = httpclient.httpclient(url,token);
//            data.put("BB",authResult);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if(authResult.equals("FAILED")){
//            System.out.println("555");
//            role1 = null;
//            sign = false;
//            result = "failed";
//        }else {
//
//            //
//            NetObjBase obj = objFactory.getNetObj(supplier,generation);

    //            role1 = dataProvider.getGroupQuotaInfo(id);
//
//            role2.put("id",role1.getRoleId());
//            role2.put("name",role1.getRoleName());
//            sign = true;
//            result= "success";
//        }
//        jsonResult.put("data",role2);
//        jsonResult.put("result", result);
//        jsonResult.put("status", sign);
//
//        return jsonResult;
//    }
    @PUT
    @Path("/suppliers/{supplier}/generations/{generation}/nets/nodes/protections/upload")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject protectImport(@RequestParam(value = "importJson") JSONArray importJson,
                                    @PathParam("supplier") String supplier,
                                    @PathParam("generation") String generation,
                                    @HeaderParam("Auth-Token") String authToken) {
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        JSONObject result = new JSONObject();
        Integer addNum = 0;
        String msg = "";
        if (obj == null){
            msg += "Supplier or Generation has error.";
        }else {
            Integer deleteNum = obj.getElementInfoService().clearNodes();
            if (deleteNum <= 0){
                msg +="ClearNodes is Failed.";
            }else {
                for (int i = 0; i < importJson.size(); i++) {
                    if (importJson.getJSONObject(i).getString("nodeName") == null || importJson.getJSONObject(i).getString("nodeName").isEmpty()){
                        msg +="NodeName is null.";
                    }else {
                        try{
                            addNum = obj.getElementInfoService().addProdectNode(importJson.getJSONObject(i).getString("nodeName"));
                        }catch (Exception e){
                            msg += "NodeName has error:" + e.getMessage();
                        }
                    }
                }
                if (addNum <= 0) {
                    msg += "AddProdectNode is Failed.";

                }
            }
        }
        if (msg.length() != 0) {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA + msg);

        } else {
            result.put("result", Constants.SUCCESS);
            result.put("msg", Constants.MSG_ADD_OK);
        }
        return result;
    }

    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/nodes/protections/download")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject protectExport(@PathParam("supplier") String supplier,
                                    @PathParam("generation") String generation,
                                    @HeaderParam("Auth-Token") String authToken) {
        String msg ="";
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        List list = new ArrayList();
        JSONObject result = new JSONObject();
        if (obj == null){
            msg += "Supplier or Generation has error.";
        }else {
            try {
                List<JSONObject> protectList = obj.getElementInfoService().getProtectListnodeName();
                if (protectList != null && protectList.size() != 0 || protectList.isEmpty()) {

                    for (JSONObject nodeName : protectList) {

                        list.add(nodeName);

                    }

                } else {
                    result.put("result", Constants.FAIL);
                    msg +="GetProtectListnodeName is Failed.";
                }
            }catch (Exception e){
                msg += "Parameters has error:" + e.getMessage();
            }
        }
        if (msg.length() != 0) {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA + msg);

        } else {
            result.put("result", Constants.SUCCESS);
            result.put("data", list);
        }
        return result;
    }

}
