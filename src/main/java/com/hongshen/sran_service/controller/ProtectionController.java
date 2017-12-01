package com.hongshen.sran_service.controller;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.common.BaseController;
import com.hongshen.sran_service.service.util.Constants;
import com.hongshen.sran_service.service.util.Httpclient;
import com.hongshen.sran_service.service.util.NetObjBase;
import com.hongshen.sran_service.service.util.NetObjFactory;
import org.springframework.beans.factory.annotation.Autowired;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

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

//        response.setHeader("Access-Control-Allow-Origin", "*");
        JSONObject result = new JSONObject();
        String url = Constants.PATH_DUMMY;
        String method = Constants.METHOD_GET;
//      if (check(url, method, authToken)) {

        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        List<JSONObject> protectList = obj.getElementInfoService().getProtectList();

        for (int i=0;i<protectList.size();i++) {

            if (protectList.get(i).getString("nodeName") != null) {

                String nodeName = String.valueOf(protectList.get(i).get("nodeName"));

                List<JSONObject> resultList = obj.getAlarmService().getNodeAlarmByName(nodeName);

                JSONObject level = obj.getQuotaService().getNodeLevelByName(nodeName);

                if(resultList.size() != 0){

                    protectList.get(i).put("alarmStatus","true");

                }else {

                    protectList.get(i).put("alarmStatus","false");


                }
                if (level != null) {

                    protectList.get(i).put("level", level );

                }else {

                    protectList.get(i).put("level", "null");

                }

            }

        }
//            System.out.println(nodeName);
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

}
