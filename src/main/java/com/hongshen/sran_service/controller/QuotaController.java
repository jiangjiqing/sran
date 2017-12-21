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
        JSONObject data = new JSONObject();

        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        String time = obj.getCacheService().getUpdateTimeForQuotaData();
        if (time == null || time == ""){
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA);

        }else {
            data.put("time", time);

            JSONObject quotas = obj.getQuotaService().getGroupQuota(groupName);

            if (quotas == null || quotas.isEmpty()){
                result.put("result", Constants.FAIL);
                result.put("msg", Constants.MSG_NO_DATA);
                return result;
            }

            List<JSONObject> formulaResultList = new ArrayList<>();
            List<JSONObject> formulaList = obj.getCacheService().getFormulaList(true);

            for (JSONObject f : formulaList) {

                JSONObject temp = new JSONObject();
                temp.put("quotaName",f.getString("quotaName"));
                temp.put("remark",f.getString("remark"));
                temp.put("hasTop10",f.getString("hasTop10"));

                String value = quotas.getString(f.getString("quotaName"));
                //String value = quotas.getString("formula" + f.getString("id"));

                if (value == null || value == "") {
                    temp.put("value", Constants.INVALID_VALUE_QUOTA);
                } else {
                    temp.put("value", value);
                }
                formulaResultList.add(temp);
            }
            data.put("quotas", formulaResultList);

            result.put("result", Constants.SUCCESS);
            result.put("data", data);
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
        JSONObject data = new JSONObject();

        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        String time = obj.getCacheService().getUpdateTimeForQuotaData();
        if (time == null || time == ""){
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA);

        }else {
            data.put("time", time);

            JSONObject quotas = obj.getQuotaService().getNodeQuota(nodeName);

            if (quotas == null || quotas.isEmpty()){
                result.put("result", Constants.FAIL);
                result.put("msg", Constants.MSG_NO_DATA);
                return result;
            }

            List<JSONObject> formulaResultList = new ArrayList<>();
            List<JSONObject> formulaList = obj.getCacheService().getFormulaList(true);

            for (JSONObject f : formulaList) {

                JSONObject temp = new JSONObject();
                temp.put("quotaName",f.getString("quotaName"));
                temp.put("remark",f.getString("remark"));
                temp.put("hasTop10",f.getString("hasTop10"));

                String value = quotas.getString(f.getString("quotaName"));
                //String value = quotas.getString("formula" + f.getString("id"));

                if (value == null || value == "") {
                    temp.put("value", Constants.INVALID_VALUE_QUOTA);
                } else {
                    temp.put("value", value);
                }
                formulaResultList.add(temp);
            }
            data.put("quotas", formulaResultList);

            result.put("result", Constants.SUCCESS);
            result.put("data", data);
        }

        return result;
    }

    // Query cell quota
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupName}/nodes/{nodeName}/cells/{cellName}/quotas")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getCellQuota(@PathParam("supplier")String supplier,
                                   @PathParam("generation")String generation,
                                   @PathParam("cellName")String cellName,
                                   @HeaderParam("Auth-Token")String authToken) {

        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();

        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        String time = obj.getCacheService().getUpdateTimeForQuotaData();
        if (time == null || time == ""){
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA);

        }else {
            data.put("time", time);

            JSONObject quotas = obj.getQuotaService().getCellQuota(cellName);

            if (quotas == null || quotas.isEmpty()){
                result.put("result", Constants.FAIL);
                result.put("msg", Constants.MSG_NO_DATA);
                return result;
            }

            List<JSONObject> formulaResultList = new ArrayList<>();
            List<JSONObject> formulaList = obj.getCacheService().getFormulaList(true);

            for (JSONObject f : formulaList) {

                JSONObject temp = new JSONObject();
                temp.put("quotaName",f.getString("quotaName"));
                temp.put("remark",f.getString("remark"));
                temp.put("hasTop10",f.getString("hasTop10"));

                String value = quotas.getString(f.getString("quotaName"));
                //String value = quotas.getString("formula" + f.getString("id"));

                if (value == null || value == "") {
                    temp.put("value", Constants.INVALID_VALUE_QUOTA);
                } else {
                    temp.put("value", value);
                }
                formulaResultList.add(temp);
            }
            data.put("quotas", formulaResultList);

            result.put("result", Constants.SUCCESS);
            result.put("data", data);
        }

        return result;
    }

    // Query group quota's bad top 10 cells
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupName}/quotas/{quotaName}/badcells")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getGroupQuotaBadTopTenCells(@PathParam("supplier")String supplier,
                                   @PathParam("generation")String generation,
                                   @PathParam("groupName")String groupName,
                                   @PathParam("quotaName")String quotaName,
                                   @HeaderParam("Auth-Token")String authToken) {

        JSONObject result = new JSONObject();

        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        List<JSONObject> cellList = obj.getQuotaService().getGroupQuotaBadTenCell(groupName,quotaName);

        if (cellList == null || cellList.isEmpty()){
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA);
        }else{
            result.put("result", Constants.SUCCESS);
            result.put("data", cellList);
        }
        return result;
    }
}
