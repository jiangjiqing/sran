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
    public JSONObject getQuotaList(@PathParam("supplier") String supplier,
                                   @PathParam("generation") String generation,
                                   @HeaderParam("Auth-Token") String authToken) {

        String msg = "";
        List<JSONObject> quotaList = new ArrayList<>();
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        if (obj == null) {
            msg += "Supplier or Generation is null.";
        } else {
            try {
                quotaList = obj.getCacheService().getFormulaList(true);

            } catch (Exception e) {
                msg += "GetFormulaList is Error.";
            }
        }
        if (quotaList == null || quotaList.isEmpty() || msg.length() != 0) {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA + msg);

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
    public JSONObject modifyQuotaInfo(@PathParam("supplier") String supplier,
                                      @PathParam("generation") String generation,
                                      @PathParam("quotaName") String quotaName,
                                      @HeaderParam("Auth-Token") String authToken,
                                      JSONObject param) {

        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        String msg = "";
        String expression = param.getString("expression");

        if (quotaName == null || quotaName.length() == 0 || quotaName.isEmpty()) {
            msg += "Formula name is null.";

        } else if (expression == null || expression.length() == 0 || expression.isEmpty()) {
            msg += "Expression is null.";

        } else if (QuotaHelper.checkExpression(expression) == false) {
            msg += "Invalid expression.";

        } else if (obj == null) {
            msg += "Supplier or Generation is null.";

        } else if(!param.getString("quotaName").equals(quotaName) && obj.getCacheService().getFormulaByName(quotaName) != null){
            msg += "Name has exist.";

        } else {
            try {
                expression = QuotaHelper.convertExpression(expression);
                param.put("expression", expression);
                param.put("quotaName", quotaName);

                int i = obj.getQuotaService().updateFormula(param);
                if (i <= 0) {
                    msg += "Update to table faild.";
                } else {
                    obj.getCacheService().resetFormulaList();
                }
            } catch (Exception e) {
                msg += "Expression or Param is Error.";
            }
        }

        if (msg.length() != 0) {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_UPDATE_FAILED + msg);

        } else {
            result.put("result", Constants.SUCCESS);
            result.put("msg", Constants.MSG_UPDATE_OK);
        }

        return result;
    }

    // Delete quota info
    @DELETE
    @Path("/suppliers/{supplier}/generations/{generation}/nets/quotas/{quotaName}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject deleteQuotaInfo(@PathParam("supplier") String supplier,
                                      @PathParam("generation") String generation,
                                      @PathParam("quotaName") String quotaName,
                                      @HeaderParam("Auth-Token") String authToken) {

        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        String msg = "";

        if (quotaName == null || quotaName.length() == 0) {
            msg += "Formula name is null.";

        } else if (obj == null) {
            msg += "Supplier or Generation is null.";
        } else {
            try {
                int i = obj.getQuotaService().DeleteFormula(quotaName);
                if (i <= 0) {
                    msg += "Delete formula table faild.";
                } else {
                    obj.getCacheService().resetFormulaList();
                }
            } catch (Exception e) {
                msg += "QuotaName is Error.";
            }

        }

        if (msg.length() != 0) {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_DELETE_FAILED + msg);

        } else {
            result.put("result", Constants.SUCCESS);
            result.put("msg", Constants.MSG_DELETE_OK);
        }

        return result;
    }

    // Query counter list
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/counters/list")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getCounterList(@PathParam("supplier") String supplier,
                                     @PathParam("generation") String generation,
                                     @HeaderParam("Auth-Token") String authToken) {

        String msg = "";
        List<JSONObject> counterList = new ArrayList<>();
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        if (obj == null) {
            msg += "Supplier or Generation is null.";
        } else {
            try {
                counterList = obj.getCacheService().getCounterList(true);
            } catch (Exception e) {
                msg += "GetCounterList is Error.";
            }
        }
        if (counterList == null || counterList.isEmpty() || msg.length() != 0) {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA + msg);

        } else {
            result.put("result", Constants.SUCCESS);
            result.put("data", counterList);
        }

        return result;
    }

    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/quotas/download")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject formulaExport(@PathParam("supplier") String supplier,
                                    @PathParam("generation") String generation,
                                    @HeaderParam("Auth-Token") String authToken) {

        String msg = "";
        JSONObject result = new JSONObject();
        List<JSONObject> formulaList = new ArrayList<>();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        if (obj == null) {
            msg += "Supplier or Generation is null.";
        } else {
            try {
                formulaList = obj.getQuotaService().getFormula(false);
            } catch (Exception e) {
                msg += "GetFormula is Error.";
            }
        }

        if (formulaList != null && formulaList.size() > 0 || msg.length() == 0) {
            result.put("result", Constants.SUCCESS);
            result.put("data", formulaList);

        } else {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_DOWNLOAD_FAILED);
        }

        return result;
    }

    @PUT
    @Path("/suppliers/{supplier}/generations/{generation}/nets/quotas/upload")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject formulaImport(@RequestParam("formulas") JSONArray formulas,
                                    @PathParam("supplier") String supplier,
                                    @PathParam("generation") String generation,
                                    @HeaderParam("Auth-Token") String authToken) {

        String msg = "";
        Integer listNum = 0;
        Integer realAddNum = 0;
        List<String> addFormulaNameList =  new ArrayList();
        List<String> addFormulaExpList =  new ArrayList();
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        if (obj == null) {
            msg += "Supplier or Generation is null.\n";
        } else {
            if (formulas == null) {
                msg += "Formulas is Null.\n";

            } else {

                List<String> oldFormulanNameList = obj.getCacheService().getFormulaNameList(false);
                List<String> formulaExp = obj.getCacheService().getFormulaExp();
                List<JSONObject> oldFormulaList = obj.getCacheService().getFormulaList(false);
                //List<JSONObject> exp = obj.getCacheService().getFormulaExpKey();
                obj.getQuotaService().deleteAllFormulas();

                listNum = formulas.size();


                for (int i = 0; i < listNum; i++) {

                    String quotaName = formulas.getJSONObject(i).getString("quotaName").replaceAll(" ","");
                    String expression = formulas.getJSONObject(i).getString("expression").replaceAll(" ","");

                    try {
                        if ( quotaName != null && !quotaName.trim().equals("") &&
                                expression != null && !expression.trim().equals("")) {

                            JSONObject formula = formulas.getJSONObject(i);

                            formula.put("quotaName",quotaName);

                            formula.put("expression",expression);

                            addFormulaNameList.add(quotaName);

                            addFormulaExpList.add(expression);

                            Integer num = obj.getQuotaService().addFormula(formula);

                            realAddNum += num;

                            if (num > 0) {
                                try{
                                    try{
                                        if(!oldFormulanNameList.contains(quotaName)&&!formulaExp.contains(expression)) {

                                            obj.getQuotaService().addGroupQuotaColumn(quotaName);
                                            obj.getQuotaService().addNodeQuotaColumn(quotaName);
                                            obj.getQuotaService().addCellQuotaColumn(quotaName);
                                        }else if(!oldFormulanNameList.contains(quotaName)&&formulaExp.contains(expression)){
                                            for(JSONObject fo:oldFormulaList){
                                                if(fo.getString("expression").equals(expression)) {
                                                    obj.getQuotaService().setGroupQuotaColumn(fo.getString("quotaName"), quotaName);
                                                    obj.getQuotaService().setNodeQuotaColumn(fo.getString("quotaName"), quotaName);
                                                    obj.getQuotaService().setCellQuotaColumn(fo.getString("quotaName"), quotaName);
                                                }
                                            }
                                        }
                                    }catch (Exception e){
                                        e.getStackTrace();
                                        msg += "QuotaHistoryTable add columns error:" + e.getMessage();
                                    }

                                    JSONObject threshold = new JSONObject();
                                    threshold.put("quotaName",quotaName);
                                    threshold.put("quotaType","0");
                                    threshold.put("threshold1","0");
                                    threshold.put("threshold2","0");
                                    threshold.put("threshold3","0");
                                    obj.getQuotaService().addCellThreshold(threshold);
                                    obj.getQuotaService().addNodeThreshold(threshold);
                                    obj.getQuotaService().addGroupThreshold(threshold);

                                }catch (Exception e){
                                    e.printStackTrace();
                                    msg += "ThresholdTable add threshold error:" + e.getMessage();
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        msg += "[" + quotaName + "] add failed.\n";
                    }
                }

                for(String f : oldFormulanNameList){

                    if(!addFormulaNameList.contains(f)){
                        // delete quota_history column
                        obj.getQuotaService().deleteGroupQuotaColumn(f);
                        obj.getQuotaService().deleteNodeQuotaColumn(f);
                        obj.getQuotaService().deleteCellQuotaColumn(f);

                        // delete threshold
                        obj.getQuotaService().deleteGroupThresholdByName(f);
                        obj.getQuotaService().deleteNodeThresholdByName(f);
                        obj.getQuotaService().deleteCellThresholdByName(f);
                    }
                }



                obj.getCacheService().resetFormulaList();

                if (realAddNum < listNum) {
                    msg += "Upload not complete.";
                }
            }
        }

        if (msg.length() == 0) {
            obj.getCacheService().resetCounterList();
            result.put("result", Constants.SUCCESS);
            result.put("msg", Constants.MSG_ADD_OK + "(Real/Total:" + realAddNum + "/" + listNum + ")");

        } else {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_ADD_FAILED + "(Real/Total:" + realAddNum + "/" + listNum + ")\n" + msg);
        }
        return result;
    }

    @PUT
    @Path("/suppliers/{supplier}/generations/{generation}/nets/counters/upload")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject counterImport(@RequestParam("counters") JSONArray counters,
                                    @PathParam("supplier") String supplier,
                                    @PathParam("generation") String generation,
                                    @HeaderParam("Auth-Token") String authToken) {

        String msg = "";
        Integer listNum = 0;
        Integer realAddNum = 0;
        String counterName = "";
        List<String> addNameList = new ArrayList();
        List<String> oldCounterList = new ArrayList();
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        if (obj == null) {
            msg += "Supplier or Generation is null.\n";
        } else {
            if (counters == null) {
                msg += "counter is Null.\n";

            } else {

                List<String> oldCounterNameList = obj.getCacheService().getCounterNameListProcessed(false);

                // get counter column's attribute
                JSONObject attribute = obj.getQuotaService().getCounterColumnAttribute(oldCounterNameList.get(0).replaceAll(" ",""));

                // clear old data
                obj.getQuotaService().deleteCounters();

                listNum = counters.size();

                for(int j = 0; j < listNum; j++){
                    if(generation.equals("wcdma")) {
                        addNameList.add(counters.getJSONObject(j).getString("name").replaceAll(" ",""));

                    }else if(generation.equals("lte")){
                        addNameList.add(counters.getJSONObject(j).getString("type").replaceAll(" ","")+
                                "_"+ counters.getJSONObject(j).getString("name").replaceAll(" ",""));
                    }
                }

                // import data
                for (int i = 0; i < listNum; i++) {

                    String name = counters.getJSONObject(i).getString("name").replaceAll(" ","");
                    String type = counters.getJSONObject(i).getString("type").replaceAll(" ","");

                    try {
                        if(name !=null && !name.trim().equals("") &&
                                type !=null && !type.trim().equals("")){


                            JSONObject counter = counters.getJSONObject(i);
                            counter.put("name",name);
                            counter.put("type",type);
                            Integer num = obj.getQuotaService().addCounter(counter);

                            if (num > 0) {
                                realAddNum += num;
                                if(generation.equals("wcdma")) {
                                    counterName = counters.getJSONObject(i).getString("name").replaceAll(" ","");

                                }else if(generation.equals("lte")){
                                    counterName = counters.getJSONObject(i).getString("type").replaceAll(" ","")+
                                            "_"+ counters.getJSONObject(i).getString("name").replaceAll(" ","");
                                }
                                try{
                                    String nullable = "";
                                    if(attribute.getString("Null").equals("YES")){
                                        nullable = "NULL";
                                    }else if(attribute.getString("Null").equals("NO")){
                                        nullable = "NOT NULL";
                                    }

                                    if(!oldCounterList.contains(counterName)){
                                        obj.getQuotaService().addColumnCounter(counterName, nullable, attribute.getString("Type"));
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                    msg += "Upload counter error:" + e.getMessage();
                                }
                            }

                        }else{
                            msg+="data is null";
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        msg += "[" + counterName + "] add failed.\n";
                    }
                }

                obj.getCacheService().resetCounterList();

                for (String c : oldCounterNameList){

                    if (!addNameList.contains(c)) {
                        obj.getQuotaService().deleteColumnCounter(c);
                    }
                }


                if (realAddNum < listNum) {
                    msg += "Upload not complete.";
                }
            }
        }

        if (msg.length() == 0) {
            obj.getCacheService().resetCounterList();
            result.put("result", Constants.SUCCESS);
            result.put("msg", Constants.MSG_ADD_OK + "(Real/Total:" + realAddNum + "/" + listNum + ")");

        } else {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_ADD_FAILED + "(Real/Total:" + realAddNum + "/" + listNum + ")\n" + msg);
        }
        return result;
    }

    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/counters/download")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject counterImport(@PathParam("supplier") String supplier,
                                    @PathParam("generation") String generation,
                                    @HeaderParam("loginName") String loginName) {
        String msg="";
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        if(obj!=null) {
            if(loginName!=null&&loginName!=""){
                List<JSONObject> counterList = obj.getQuotaService().getCounterList();
                if (counterList != null && counterList.size() > 0) {
                    result.put("result", Constants.SUCCESS);
                    result.put("data", counterList);
                } else {
                    result.put("result", Constants.FAIL);
                    result.put("msg", Constants.MSG_NO_DATA);
                }
            }else{
                msg+="loginName null";
            }
        }else{
            msg+="Supplier or Generation has error.";
        }

        if(msg.equals("")){
            result.put("result", Constants.FAIL);
            result.put("msg",msg);
        }

        return result;
    }

}
