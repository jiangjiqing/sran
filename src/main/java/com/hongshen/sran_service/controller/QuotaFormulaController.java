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
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        if (obj == null) {
            msg += "Supplier or Generation is null.\n";
        } else {
            if (formulas == null) {
                msg += "Formulas is Null.\n";

            } else {
                listNum = formulas.size();

                for (int i = 0; i < listNum; i++) {
                    try {
                        if (formulas.getJSONObject(i).getString("quotaName") != null
                                && !formulas.getJSONObject(i).getString("quotaName").equals("")
                                && formulas.getJSONObject(i).getString("expression") != null
                                && !formulas.getJSONObject(i).getString("expression").equals("")) {

                            realAddNum += obj.getQuotaService().addFormula(formulas.getJSONObject(i));

                            if (realAddNum > 0) {
                                try{
                                    obj.getQuotaService().addColumnGroup(formulas.getJSONObject(i).getString("expression"));
                                    obj.getQuotaService().addColumnNode(formulas.getJSONObject(i).getString("expression"));
                                    obj.getQuotaService().addColumnCell(formulas.getJSONObject(i).getString("expression"));
                                }catch (Exception e){
                                    e.getMessage();
                                }
                            }
                            obj.getCacheService().resetFormulaList();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();

                        msg += "[" + formulas.getJSONObject(i).getString("quotaName") + "] add failed.\n";
                    }
                }

                if (realAddNum < listNum) {
                    msg += "Upload has error.";
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
    public JSONObject counterImport(@RequestParam("formulas") JSONArray counters,
                                    @PathParam("supplier") String supplier,
                                    @PathParam("generation") String generation,
                                    @HeaderParam("Auth-Token") String authToken) {

        String msg = "";
        Integer listNum = 0;
        Integer realAddNum = 0;
        int AddNum =0;
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        if (obj == null) {
            msg += "Supplier or Generation is null.\n";
        } else {
            if (counters == null) {
                msg += "counter is Null.\n";

            } else {
                listNum = counters.size();

                for (int i = 0; i < listNum; i++) {
                    try {
                        if(counters.getJSONObject(i).getString("name")!=null&&counters.getJSONObject(i).getString("name")!=""
                                &&counters.getJSONObject(i).getString("type")!=null&&counters.getJSONObject(i).getString("type")!=""){


                            realAddNum = obj.getQuotaService().addCounter(counters.getJSONObject(i));

                            if (counters.getJSONObject(i).getString("name")!=null&&realAddNum > 0) {
                                AddNum= AddNum+realAddNum;
                                obj.getQuotaService().addColumnCounter(counters.getJSONObject(i).getString("name"));
                            }
                            obj.getCacheService().resetCounterList();
                        }else{
                            msg+="data is null";
                        }
                    } catch (Exception e) {
                        e.printStackTrace();

                        msg += "[" + counters.getJSONObject(i).getString("name") + "] add failed.\n";
                    }
                }

                if (AddNum < listNum) {
                    msg += "Upload has error.";
                }
            }
        }

        if (msg.length() == 0) {
            obj.getCacheService().resetCounterList();
            result.put("result", Constants.SUCCESS);
            result.put("msg", Constants.MSG_ADD_OK + "(Real/Total:" + AddNum + "/" + listNum + ")");

        } else {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_ADD_FAILED + "(Real/Total:" + AddNum + "/" + listNum + ")\n" + msg);
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
