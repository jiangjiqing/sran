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

    // ============================ map =====================================

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
        JSONObject groupQuota = obj.getQuotaService().getGroupQuotaByGroupName(groupName);

        List<JSONObject> quotaList = new ArrayList<JSONObject>();
        JSONObject tempJson = new JSONObject();//TODO

        List<JSONObject> formulaList = obj.getCacheService().getFormulaList(true);
        for (JSONObject f : formulaList) {
            String value = groupQuota.getString("fomular" + f.get("id"));
            tempJson.put("quotaName", f.getString("quotaName"));
            tempJson.put("value", value);
            tempJson.put("remark", f.getString("remark"));
//                tempJson.put("topStatus",f.getString("top10"));
            tempJson.put("topStatus", false);//TODO

            quotaList.add(tempJson);
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
/*
    / query node quota
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupName}/nodes/{nodeName}/quotas")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getNodeQuota(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                   @PathParam("groupname")String groupname, @PathParam("nodename")String nodename,
                                   @HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();

        String url = Constants.NODE_QUOTA;
        String method = Constants.METHOD_GET;

        if (check(url, method, authToken)) {

            NetObjBase base = objFactory.getNetObj(supplier, generation);

            //QuotaService
            NodeService nodeService = base.getNodeService();

            List<JSONObject> resultQuotaNode = nodeService.getNodeQuotaByNodeName(nodename);

            if (!resultQuotaNode.isEmpty()){

                result.put("data", resultQuotaNode);
                result.put("status", Constants.SUCCESS);
            } else {

                result.put("status", Constants.FAIL);
            }

            return result;
        } else {

            return result;
        }
    }

    // query cell quota
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupName}/nodes/{nodeName}/cells/{cellName}/quotas")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getCellQuota(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                   @PathParam("groupname")String groupname, @PathParam("nodename")String nodename,
                                   @PathParam("cellname")String cellname, @HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();

        String url = Constants.CELL_QUOTA;
        String method = Constants.METHOD_GET;

        if (check(url, method, authToken)) {

            NetObjBase base = objFactory.getNetObj(supplier, generation);

            //QuotaService
            CellService cellService = base.getCellService();

            List<JSONObject> resultQuotaCell = cellService.getCellQuotaByCellName(cellname);

            if (!resultQuotaCell.isEmpty()){

                result.put("data", resultQuotaCell);
                result.put("status", Constants.SUCCESS);
            } else {

                result.put("status", Constants.FAIL);
            }

            return result;
        } else {

            return result;
        }
    }

    // ============================ quota =====================================
    // query quota list
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/quotas")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getQuotaList(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                   @HeaderParam("Auth-Token")String authToken){
        JSONObject result = new JSONObject();
        //QuotaService
        return result;
    }

    // modify quota info
    @PUT
    @Path("/suppliers/{supplier}/generations/{generation}/nets/quotas/{quotaName}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject modifyQuotaInfo(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                   @HeaderParam("Auth-Token")String authToken){
        JSONObject result = new JSONObject();
        //QuotaService
        return result;
    }

    // delete quota info
    @DELETE
    @Path("/suppliers/{supplier}/generations/{generation}/nets/quotas/{quotaName}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject deleteQuotaInfo(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                   @HeaderParam("Auth-Token")String authToken){
        JSONObject result = new JSONObject();
        //QuotaService
        return result;
    }

    // ============================ counter =====================================
    // query counter list
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/counters/list")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getcounterList(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                   @HeaderParam("Auth-Token")String authToken){
        JSONObject result = new JSONObject();
        //QuotaService
        return result;
    }
*/
}
