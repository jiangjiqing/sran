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
import java.util.Iterator;
import java.util.List;

/**
 * Created by poplar on 11/13/17.
 */
@Path("/sran/service/net/quota")
public class QuotaController {

    @Autowired
    private NetObjFactory objFactory;

    @Autowired
    private Httpclient httpclient;

    // query group quota
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupName}/quotas")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getGroupQuota(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                    @PathParam("groupName")String groupName, @HeaderParam("Auth-Token")String authToken) {

        JSONObject result = new JSONObject();

        String url = Constants.PATH_DUMMY;
        String method = Constants.METHOD_GET;

//        if (check(url, method, authToken)) {

        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        JSONObject groupQuota = obj.getQuotaService().getGroupQuotaByName(groupName);

        List<JSONObject> quotaList = new ArrayList<JSONObject>();
        List<JSONObject> formulaList = obj.getCacheService().getFormulaList(true);

        for (JSONObject f : formulaList) {

            JSONObject quota = new JSONObject();

            quota.put("value", groupQuota.getString("formula" + f.get("id")));
            quota.put("time", groupQuota.getString("time"));
            quota.put("quotaName", f.getString("quotaName"));
            quota.put("remark", f.getString("remark"));
            quota.put("topStatus",f.getString("hasTop10"));

            quotaList.add(quota);
        }

        if (!quotaList.isEmpty()) {

            result.put("data", quotaList);
            result.put("status", Constants.SUCCESS);
        } else {

            result.put("status", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA);
        }

        return result;

//
//        } else {
//			  result.put("result", Constants.FAIL);
//			  result.put("msg", Constants.MSG_NO_PERMISSION);
//            return result;
//        }
    }

    // query node quota
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupName}/nodes/{nodeName}/quotas")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getNodeQuota(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                    @PathParam("nodeName")String nodeName, @HeaderParam("Auth-Token")String authToken) {

        JSONObject result = new JSONObject();

        String url = Constants.PATH_DUMMY;
        String method = Constants.METHOD_GET;

//        if (check(url, method, authToken)) {

        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        JSONObject nodeQuota = obj.getQuotaService().getNodeQuotaByName(nodeName);

        List<JSONObject> quotaList = new ArrayList<JSONObject>();
        List<JSONObject> formulaList = obj.getCacheService().getFormulaList(true);

        for (JSONObject f : formulaList) {

            JSONObject quota = new JSONObject();
            quota.put("value", nodeQuota.getString("formula" + f.get("id")));
            quota.put("time", nodeQuota.getString("time"));
            quota.put("quotaName", f.getString("quotaName"));
            quota.put("remark", f.getString("remark"));

            quotaList.add(quota);
        }

        if (!quotaList.isEmpty()) {

            result.put("data", quotaList);
            result.put("status", Constants.SUCCESS);
        } else {

            result.put("status", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA);
        }

        return result;

//
//        } else {
//			  result.put("result", Constants.FAIL);
//			  result.put("msg", Constants.MSG_NO_PERMISSION);
//            return result;
//        }
    }

    // query cell quota
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupName}/nodes/{nodeName}/cells/{cellName}/quotas")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getCellQuota(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                   @PathParam("cellName")String cellName, @HeaderParam("Auth-Token")String authToken) {

        JSONObject result = new JSONObject();

        String url = Constants.PATH_DUMMY;
        String method = Constants.METHOD_GET;

//        if (check(url, method, authToken)) {

        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        JSONObject cellQuota = obj.getQuotaService().getCellQuotaByName(cellName);

        List<JSONObject> quotaList = new ArrayList<JSONObject>();
        List<JSONObject> formulaList = obj.getCacheService().getFormulaList(true);

        for (JSONObject f : formulaList) {

            JSONObject quota = new JSONObject();
            quota.put("value", cellQuota.getString("formula" + f.get("id")));
            quota.put("time", cellQuota.getString("time"));
            quota.put("quotaName", f.getString("quotaName"));
            quota.put("remark", f.getString("remark"));

            quotaList.add(quota);
        }

        if (!quotaList.isEmpty()) {

            result.put("data", quotaList);
            result.put("status", Constants.SUCCESS);
        } else {

            result.put("status", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA);
        }

        return result;
//
//        } else {
//			  result.put("result", Constants.FAIL);
//			  result.put("msg", Constants.MSG_NO_PERMISSION);
//            return result;
//        }
    }

}
