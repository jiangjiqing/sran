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
@Path("/sran/service/net/quota")
public class QuotaController {

    @Autowired
    private NetObjFactory objFactory;

    // Query group quota
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupName}/quotas")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getGroupQuota(@PathParam("supplier")String supplier,
                                    @PathParam("generation")String generation,
                                    @PathParam("groupName")String groupName,
                                    @HeaderParam("Auth-Token")String authToken) {

        JSONObject result = new JSONObject();

        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        JSONObject groupQuota = obj.getQuotaService().getGroupQuota(groupName);

        List<JSONObject> quotaList = new ArrayList<JSONObject>();

        //TODO :get data from formula and history
        List<JSONObject> formulaList = obj.getCacheService().getFormulaList(true);

        for (JSONObject f : formulaList) {

            JSONObject quota = new JSONObject();

            quota.putAll(quota);
            quota.put("value", Constants.INVALID_VALUE_QUOTA);

            quotaList.add(quota);
        }

        if (quotaList.isEmpty()) {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA);

        } else {
            result.put("result", Constants.SUCCESS);
            result.put("data", quotaList);
        }

        return result;
    }

    // Query node quota
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupName}/nodes/{nodeName}/quotas")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getNodeQuota(@PathParam("supplier")String supplier,
                                   @PathParam("generation")String generation,
                                   @PathParam("nodeName")String nodeName,
                                   @HeaderParam("Auth-Token")String authToken) {

        JSONObject result = new JSONObject();

        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        JSONObject nodeQuota = obj.getQuotaService().getNodeQuota(nodeName);

        List<JSONObject> quotaList = new ArrayList<JSONObject>();

        //TODO :get data from formula and history
        List<JSONObject> formulaList = obj.getCacheService().getFormulaList(true);

        for (JSONObject f : formulaList) {

            JSONObject quota = new JSONObject();

            quota.putAll(quota);
            quota.put("value", Constants.INVALID_VALUE_QUOTA);

            quotaList.add(quota);
        }

        if (quotaList.isEmpty()) {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA);

        } else {
            result.put("result", Constants.SUCCESS);
            result.put("data", quotaList);
        }

        return result;
    }

    // Query cell quota
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupName}/nodes/{nodeName}/cells/{cellName}/quotas")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getCellQuota(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                   @PathParam("cellName")String cellName, @HeaderParam("Auth-Token")String authToken) {

        JSONObject result = new JSONObject();

        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        JSONObject cellQuota = obj.getQuotaService().getCellQuota(cellName);

        List<JSONObject> quotaList = new ArrayList<JSONObject>();

        //TODO :get data from formula and history
        List<JSONObject> formulaList = obj.getCacheService().getFormulaList(true);

        for (JSONObject f : formulaList) {

            JSONObject quota = new JSONObject();

            quota.putAll(quota);
            quota.put("value", Constants.INVALID_VALUE_QUOTA);

            quotaList.add(quota);
        }

        if (quotaList.isEmpty()) {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA);

        } else {
            result.put("result", Constants.SUCCESS);
            result.put("data", quotaList);
        }

        return result;
    }

}
