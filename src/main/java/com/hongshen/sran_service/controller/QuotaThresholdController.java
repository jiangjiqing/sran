package com.hongshen.sran_service.controller;

/**
 * Created by poplar on 11/13/17.
 */
import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.common.BaseController;
import com.hongshen.sran_service.service.util.Constants;
import com.hongshen.sran_service.service.util.NetObjBase;
import com.hongshen.sran_service.service.util.NetObjFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/sran/service/net/threshold")
public class QuotaThresholdController extends BaseController{
    @Autowired
    private NetObjFactory objFactory;
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/{level}/thresholds")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getGroupList(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                   @HeaderParam("Auth-Token")String authToken,@PathParam("level")String level) {
        System.out.println(level);
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        JSONObject result = new JSONObject();
        List<JSONObject> list = new ArrayList<>();

        switch (level)
        {
            case "groups":
                List<JSONObject> thresholdGroupList = obj.getCacheService().getThresholdGroupList();
                    list = getvalue(thresholdGroupList);
            break;

            case "nodes":
                List<JSONObject> thresholdNodeList = obj.getCacheService().getThresholdNodeList();
                    list = getvalue(thresholdNodeList);
            break;

            case "cells":
                List<JSONObject> thresholdCellList = obj.getCacheService().getThresholdCellList();

                    list = getvalue(thresholdCellList);
                break;

            default :
    }
        if(!list.isEmpty()){
            result.put("result", Constants.SUCCESS);
            result.put("data",list);
        }else{
            result.put("result", Constants.FAIL);
        }
        return result;
    }

 static List<JSONObject> getvalue(List<JSONObject> jsonList){
     JSONObject result = new JSONObject();
     List<JSONObject> list = new ArrayList<>();
     for(JSONObject json:jsonList){
         result.put("quotaName",json.getString("quota_name"));
         result.put("quotaType",json.getString("quota_type"));
         result.put("threshold1",json.getString("threshold_1"));
         result.put("threshold2",json.getString("threshold_2"));
         result.put("threshold3",json.getString("threshold_3"));
         result.put("quotaUnit",json.getString("quota_unit"));
         list.add(result);
            }
        return list;
     }

    @PUT
    @Path("/suppliers/{supplier}/generations/{generation}/nets/{level}/quotas/{quotaName}/thresholds")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getGroupList(@RequestParam(value="quotaName") JSONObject quotaName1,
                                   @PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                   @HeaderParam("Auth-Token")String authToken, @PathParam("level")String level,
                                   @PathParam("quotaName")String quotaName) {

    System.out.println(quotaName1.getString("quotaName"));
     return null;
    }

}
