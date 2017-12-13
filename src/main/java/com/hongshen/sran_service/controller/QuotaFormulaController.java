package com.hongshen.sran_service.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.service.util.Constants;
import com.hongshen.sran_service.service.util.NetObjBase;
import com.hongshen.sran_service.service.util.NetObjFactory;
import com.hongshen.sran_service.service.util.QuotaHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by poplar on 11/13/17.
 */
@Path("/sran/service/net/formula")
public class QuotaFormulaController {

    @Autowired
    private NetObjFactory objFactory;

    // Query formula list
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/quotas")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getQuotaList(@PathParam("supplier")String supplier,
                                   @PathParam("generation")String generation,
                                   @HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();

        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        List<JSONObject> quotaList = obj.getCacheService().getFormulaList(true);

        if (quotaList == null || quotaList.isEmpty()) {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA);

        } else {
            result.put("result", Constants.SUCCESS);
            result.put("data", quotaList);
        }

        return result;
    }

    // Update quota info
    @PUT
    @Path("/suppliers/{supplier}/generations/{generation}/nets/quotas/{quotaName}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject modifyQuotaInfo(@PathParam("supplier")String supplier,
                                      @PathParam("generation")String generation,
                                      @PathParam("quotaName")String quotaName,
                                      @HeaderParam("Auth-Token")String authToken,
                                      JSONObject param){

        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        String expression = QuotaHelper.convertExpression(param.getString("expression"));
        if (expression.equals("")){
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_EXPRESSION_INVALID);
            return result;
        }
        //List<JSONObject> quotaList = obj.getCacheService().;
        //TODO
        return result;
    }

    // Delete quota info
    @DELETE
    @Path("/suppliers/{supplier}/generations/{generation}/nets/quotas/{quotaName}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject deleteQuotaInfo(@PathParam("supplier")String supplier,
                                      @PathParam("generation")String generation,
                                      @HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();
        //TODO
        return result;
    }

    // Query counter list
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/counters/list")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getCounterList(@PathParam("supplier")String supplier,
                                     @PathParam("generation")String generation,
                                     @HeaderParam("Auth-Token")String authToken){
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        List<JSONObject> counterList = obj.getCacheService().getCounterList(true);

        if (counterList == null || counterList.isEmpty()) {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA);

        } else {
            result.put("result", Constants.SUCCESS);
            result.put("data", counterList);
        }

        return result;
    }

    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/quotas/download")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject formulaExport(@PathParam("supplier")String supplier,
                                    @PathParam("generation")String generation,
                                    @HeaderParam("Auth-Token")String authToken) {
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        List<JSONObject> formulaList = obj.getQuotaService().getFormula(false);
        if(formulaList!=null&&formulaList.size()>0){
            result.put("result",Constants.MSG_DOWNLOAD_FORMULAS_OK);
            result.put("result",Constants.SUCCESS);
            result.put("data",formulaList);
        }else {
            result.put("result",Constants.MSG_DOWNLOAD_FORMULAS_FAILED);
            result.put("result",Constants.FAIL);
        }

        return result;
    }

    @PUT
    @Path("/suppliers/{supplier}/generations/{generation}/nets/quotas/download")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject formulaImport(@RequestParam("formulas")JSONArray formulas,
                                    @PathParam("supplier")String supplier,
                                    @PathParam("generation")String generation,
                                    @HeaderParam("Auth-Token")String authToken) {
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        Integer addNum=0;
        if(formulas!=null) {
            Integer delNum =  obj.getQuotaService().deleteFormulas();

            for (int i = 0; i < formulas.size(); i++) {
                try {
                    addNum = obj.getQuotaService().addFormula(formulas.getJSONObject(i));
                } catch (Exception e) {
                    result.put("result",Constants.FAIL);
                    result.put("msg","DB Exception");
                }
            }
            if(addNum>0){
                obj.getCacheService().resetCounterList();
                result.put("result",Constants.SUCCESS);
                result.put("msg",Constants.MSG_ADD_OK);
            }
        }else {
            result.put("result",Constants.FAIL);
            result.put("msg",Constants.MSG_ADD_FAILED);
        }
        return result;
    }
}
