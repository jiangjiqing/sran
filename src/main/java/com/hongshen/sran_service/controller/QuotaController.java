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

        String msg = "";
        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        if (obj == null){
            msg += "Supplier or Generation has error.";

        }else if (groupName == null || groupName.length() == 0) {
            msg +="GroupName is null.";

        }else{

            String time = obj.getCacheService().getUpdateTimeForQuotaData();
            if (time == null || time == ""){
                msg +="Time is null.";

            }else {
                data.put("time", time);
                try {
                    JSONObject quotas = obj.getQuotaService().getGroupQuota(groupName);

                    if (quotas == null || quotas.isEmpty()) {
                        msg += "Quotas is null.";

                    }else {
                        try {
                            List<JSONObject> formulaResultList = new ArrayList<>();
                            List<JSONObject> formulaList = obj.getCacheService().getFormulaList(true);

                            for (JSONObject f : formulaList) {

                                JSONObject temp = new JSONObject();
                                temp.put("quotaName", f.getString("quotaName"));
                                temp.put("remark", f.getString("remark"));
                                temp.put("hasTop10", f.getString("hasTop10"));

                                String value = quotas.getString(f.getString("quotaName"));
                                //String value = quotas.getString("formula" + f.getString("id"));

                                if (value == null || value == "") {
                                    temp.put("value", Constants.INVALID_VALUE_QUOTA);
                                } else {
                                    temp.put("value", Double.parseDouble(value));
                                }
                                formulaResultList.add(temp);
                            }
                            data.put("quotas", formulaResultList);

                        }catch (Exception e){
                            msg += "FormulaList has error:" + e.getMessage();
                        }
                    }
                }catch (Exception e){
                    msg += "Quotas has error:" + e.getMessage();
                }
            }
        }

        if (msg.length() == 0){
            result.put("result", Constants.SUCCESS);
            result.put("data", data);

        }else{
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA + msg);
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

        String msg = "";
        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();

        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        if (obj == null){
            msg += "Supplier or Generation has error.";

        }else if (nodeName == null || nodeName.length() == 0) {
            msg +="NodeName is null.";

        }else {
            String time = obj.getCacheService().getUpdateTimeForQuotaData();
            if (time == null || time == ""){
                msg +="Time is null.";

            }else {
                data.put("time", time);
                try {
                    JSONObject quotas = obj.getQuotaService().getNodeQuota(nodeName);
                    if (quotas == null || quotas.isEmpty()) {
                        msg += "Quotas is null.";

                    }else {
                        try {
                            List<JSONObject> formulaResultList = new ArrayList<>();

                            List<JSONObject> formulaList = obj.getCacheService().getFormulaList(true);

                            for (JSONObject f : formulaList) {

                                JSONObject temp = new JSONObject();
                                temp.put("quotaName", f.getString("quotaName"));
                                temp.put("remark", f.getString("remark"));
                                temp.put("hasTop10", f.getString("hasTop10"));

                                String value = quotas.getString(f.getString("quotaName"));
                                //String value = quotas.getString("formula" + f.getString("id"));

                                if (value == null || value == "") {
                                    temp.put("value", Constants.INVALID_VALUE_QUOTA);
                                } else {
                                    temp.put("value", Double.parseDouble(value));
                                }
                                formulaResultList.add(temp);
                            }
                            data.put("quotas", formulaResultList);

                        } catch (Exception e) {
                            msg += "FormulaList has error:" + e.getMessage();
                        }
                    }
                } catch (Exception e){
                    msg += "Quotas has error:" + e.getMessage();
                }
            }
        }

        if (msg.length() == 0){
            result.put("result", Constants.SUCCESS);
            result.put("data", data);

        }else{
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA + msg);
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

        String msg = "";
        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();

        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        if (obj == null){
            msg += "Supplier or Generation has error.";

        }else if (cellName == null || cellName.length() == 0) {
            msg +="CellName is null.";

        }else {
            String time = obj.getCacheService().getUpdateTimeForQuotaData();
            if (time == null || time == ""){
                msg +="Time is null.";

            }else {
                data.put("time", time);
                try {

                    JSONObject quotas = obj.getQuotaService().getCellQuota(cellName);

                    if (quotas == null || quotas.isEmpty()){
                        msg += "Quotas is null.";

                    }else {
                        try {
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
                                    temp.put("value", Double.parseDouble(value));
                                }
                                formulaResultList.add(temp);
                            }
                            data.put("quotas", formulaResultList);

                        } catch (Exception e) {
                            msg += "FormulaList has error:" + e.getMessage();
                        }
                    }
                } catch (Exception e){
                    msg += "Quotas has error:" + e.getMessage();
                }
            }
        }

        if (msg.length() == 0){
            result.put("result", Constants.SUCCESS);
            result.put("data", data);

        }else{
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA + msg);
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

        String msg = "";
        JSONObject result = new JSONObject();
        List<JSONObject> cellList = new ArrayList<>();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        if (obj == null){
            msg += "Supplier or Generation has error.";

        }else if (groupName == null || groupName.length() == 0) {
            msg +="GroupName is null.";

        }else if (quotaName == null || quotaName.length() == 0) {
            msg += "QuotaName is null.";

        }else {
            try {
                cellList = obj.getQuotaService().getGroupQuotaBadTenCell(groupName, quotaName);
                if (cellList == null || cellList.isEmpty()) {
                    msg += "CellList is null.";
                }
            } catch (Exception e) {
                msg += "CellList has error:" + e.getMessage();
            }
        }

        if (msg.length() == 0){
            result.put("result", Constants.SUCCESS);
            result.put("data", result);

        }else{
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA + msg);
        }

        return result;
    }
}
