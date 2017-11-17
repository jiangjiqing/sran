package com.hongshen.sran_service.controller;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.service.util.Constants;
import com.hongshen.sran_service.service.util.Httpclient;
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

    @Autowired
    private Httpclient httpclient;

    // query all alarm log
    @GET
    @Path("/suppliers/{supplier}/nets/alarms")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getAllAlarms (@PathParam("supplier")String supplier, @HeaderParam("Auth-Token")String authToken) {
	
		JSONObject result = new JSONObject();
		
        String url = Constants.PATH_DUMMY;
        String method = Constants.METHOD_GET;
		
//        if (check(url, method, authToken)) {

			NetObjBase obj_Wcdma = objFactory.getNetObj(supplier, Constants.WCDMA);
			NetObjBase obj_Lte = objFactory.getNetObj(supplier, Constants.LTE);
			JSONObject resultWcdma = new JSONObject();
			JSONObject resultLte = new JSONObject();
			List<JSONObject> list = new ArrayList<JSONObject>();
			int dataCount = 0;
			
            List<JSONObject> resultList_Wcdma = obj_Wcdma.getAlarmService().getAllAlarmInfo();
			List<JSONObject> resultList_Lte = obj_Lte.getAlarmService().getAllAlarmInfo();
			
			// add 3G data
            if (!resultList_Wcdma.isEmpty() || !resultList_Lte.isEmpty()){
                if (!resultList_Wcdma.isEmpty()){
                    resultWcdma.put("generation", Constants.WCDMA);
                    resultWcdma.put("alarms", resultList_Wcdma);
                    list.add(resultWcdma);
                }
			
			// add 4G data
                if (!resultList_Lte.isEmpty()){
                    resultLte.put("generation", Constants.LTE);
                    resultLte.put("alarms", resultList_Lte);
                    list.add(resultLte);
                }

                result.put("result", Constants.SUCCESS);
                result.put("data",list);
			} else {
                result.put("result", Constants.FAIL);
				result.put("msg", Constants.MSG_NO_DATA);
            }

            return result;
			
//        } else {
//
//			result.put("status", Constants.FAIL);
//			result.put("msg", FAIL_MSG_NO_PERMISSION);
//            return result;
//        }
    }

    // query group alarm log 
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupname}/alarms")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getGroupAlarms(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                    @PathParam("groupname")String groupname, @HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();

        String url = Constants.PATH_DUMMY;
        String method = Constants.METHOD_GET;

//        if (check(url, method, authToken)) {

            NetObjBase obj = objFactory.getNetObj(supplier, generation);

			//TODO
//			List<JSONObject> resultList;
            //ElementInfoService
            List<JSONObject> resultList = obj.getAlarmService().getGroupAlarmByGroupName(groupname);
			
            if (!resultList.isEmpty()){
                result.put("data", resultList);
                result.put("status", Constants.SUCCESS);
            } else {
                result.put("status", Constants.FAIL);
				result.put("msg", Constants.MSG_NO_DATA);
            }

            return result;
			
//        } else {
//
//			result.put("status", Constants.FAIL);
//			result.put("msg", FAIL_MSG_NO_PERMISSION);
//            return result;
//        }
    }

    // query node alarm log
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupname}/nodes/{nodename}/alarms")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getNodeAlarms(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                   @PathParam("groupname")String groupName, @PathParam("nodename")String nodeName,
                                   @HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();

        String url = Constants.PATH_DUMMY;
        String method = Constants.METHOD_GET;

//        if (check(url, method, authToken)) {

            NetObjBase obj = objFactory.getNetObj(supplier, generation);
			
			//TODO
//			List<JSONObject> resultList;
            List<JSONObject> resultList = obj.getAlarmService().getNodeAlarmByNodeName(nodeName);

            if (!resultList.isEmpty()){

                result.put("data", resultList);
                result.put("status", Constants.SUCCESS);
            } else {

                result.put("status", Constants.FAIL);
				result.put("msg", Constants.MSG_NO_DATA);
            }

            return result;
			
//        } else {
//
//			result.put("status", Constants.FAIL);
//			result.put("msg", Constants.MSG_NO_PERMISSION);
//            return result;
//        }
    }

    // query cell alarm log
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupname}/nodes/{nodename}/cells/{cellname}/alarms")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getCellAlarms(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                   @PathParam("groupname")String groupName, @PathParam("nodename")String nodeName,
                                   @PathParam("cellname")String cellName, @HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();

        String url = Constants.PATH_DUMMY;
        String method = Constants.METHOD_GET;

//        if (check(url, method, authToken)) {

            NetObjBase obj = objFactory.getNetObj(supplier, generation);
            
			//TODO
//			List<JSONObject> resultList;
            List<JSONObject> resultList = obj.getAlarmService().getCellAlarmByCellName(cellName);

            if (!resultList.isEmpty()){

                result.put("data", resultList);
                result.put("status", Constants.SUCCESS);
            } else {
				
                result.put("status", Constants.FAIL);
				result.put("msg", Constants.MSG_NO_DATA);
            }

            return result;

//        } else {
//
//			result.put("status", Constants.FAIL);
//			result.put("msg", FAIL_MSG_NO_PERMISSION);
//            return result;
//        }
    }
}
