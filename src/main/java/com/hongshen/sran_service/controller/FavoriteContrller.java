package com.hongshen.sran_service.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.service.util.Constants;
import com.hongshen.sran_service.service.util.Httpclient;
import com.hongshen.sran_service.service.util.NetObjBase;
import com.hongshen.sran_service.service.util.NetObjFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by poplar on 11/13/17.
 */
@Path("/sran/service/net/favorite")
public class FavoriteContrller {

    @Autowired
    private NetObjFactory objFactory;

    @Autowired
    private Httpclient httpclient;

    //    Query collection network element list
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/favorites/mapinfos")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getProtectionList(@PathParam("supplier")String supplier,
                                        @PathParam("generation")String generation,
                                        @HeaderParam("loginName")String loginName) {

        String msg = "";
        JSONObject result = new JSONObject();
        String tableNameBase = "unicom_favorite_" + generation + "_";
        String tableNameLike = "%" + tableNameBase + loginName + "%";
        String tableName = tableNameBase + loginName;
        List<JSONObject> favoriteList = new ArrayList<>();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        if (obj == null){
            msg += "Supplier or Generation has error.";

        }else {
            JSONObject table = obj.getElementInfoService().getTable(tableNameLike);

            if (table == null || table.isEmpty() || table.size() == 0) {
                msg += "Table is Null.";

            } else {

                favoriteList = obj.getElementInfoService().getFavoriteList(tableName);

                for (JSONObject node : favoriteList) {

                    String nodeName = String.valueOf(node.getString("nodeName"));

                    if (nodeName != null || nodeName.trim().length() > 0) {

                        JSONObject level = obj.getQuotaService().getNodeLevel(nodeName);

                        if (level != null && level.getIntValue("level") != -1) {

                            node.put("level", level.getIntValue("level"));

                        } else {

                            node.put("level", Constants.INVALID_VALUE_LEVEL);
                        }

                    }

                }
            }
        }
        if (favoriteList == null || favoriteList.isEmpty() || msg.length() != 0) {

            result.put("msg", Constants.MSG_NO_DATA + msg);
            result.put("result", Constants.FAIL);

        } else {

            result.put("data", favoriteList);
            result.put("result", Constants.SUCCESS);

        }
        return result;

    }
    //    Cancel the status of network element collection
    @DELETE
    @Path("/suppliers/{supplier}/generations/{generation}/nets/{level}/{name}/favorites")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject cancelCollection(@PathParam("supplier")String supplier,
                                       @PathParam("generation")String generation,
                                       @PathParam("level")String level,
                                       @PathParam("name")String name,
                                       @HeaderParam("loginName")String loginName) {

        JSONObject result = new JSONObject();

        String msg = "";
        String tableNameBase = "unicom_favorite_"+generation+"_";
        String gettableName = "%"+tableNameBase+loginName+"%";
        String tableName = tableNameBase+loginName;
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        if (obj == null){
            msg += "Supplier or Generation has error.";

        }else {
            JSONObject table = obj.getElementInfoService().getTable(gettableName);
            if (table == null || table.isEmpty()){
                msg += "Table is Null.";

            }else {
                int i = 0;
                switch (level){
                    case Constants.LEVEL_GROUP:
                        i = obj.getElementInfoService().deleteFavoriteNodes(tableName, name);
                        if (i <= 0) {
                            msg += "Delete Node is failed.";
                        }
                        break;

                    case Constants.LEVEL_NODE:
                        i = obj.getElementInfoService().deleteFavoriteNode(tableName, name);
                        if (i <= 0) {
                            msg += "Delete Node is failed.";
                        }
                        break;

                    default:
                        msg += "Level has error.";
                        break;
                }
            }
        }

        if (msg.length() == 0){
            result.put("result", Constants.SUCCESS);
            result.put("msg", Constants.MSG_DELETE_OK);

        }else{
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_DELETE_FAILED + msg);
        }

        return result;
    }

    //    Add the status of network element collection
    @POST
    @Path("/suppliers/{supplier}/generations/{generation}/nets/{level}/{name}/favorites")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject addCollection(@PathParam("supplier")String supplier,
                                    @PathParam("generation")String generation,
                                    @PathParam("level")String level,
                                    @PathParam("name")String name,JSONObject param,
                                    @HeaderParam("loginName")String loginName) {

        JSONObject result = new JSONObject();
        String msg = "";
        String tableNameBase = "unicom_favorite_"+generation+"_";
        String gettableName = "%"+tableNameBase+loginName+"%";
        String tableName = tableNameBase+loginName;
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        if (obj == null){
            msg += "Supplier or Generation has error.";

        }else {
            JSONObject table = obj.getElementInfoService().getTable(gettableName);
            Boolean tableExist = false;

            if (table == null || table.size() ==0 || table.isEmpty()) {
                int j = obj.getElementInfoService().createTable(tableName);
                if (j <= 0) {
                    msg += "Create table failed.";
                }
            } else {
                tableExist = true;
            }

            if (tableExist) {
                try {
                    switch (level) {
                        case Constants.LEVEL_GROUP:

                                List<JSONObject> nodeNames = obj.getElementInfoService().getNodeListByGroup(name);

                                if (nodeNames.size() != 0 || nodeNames.isEmpty()) {

                                    int i = obj.getElementInfoService().addFavoriteNodes(tableName, nodeNames);

                                    if (i <= 0) {
                                        msg += "Add nodes is failed.";
                                    }

                                } else {
                                    msg += "Node is null";
                                }
                            break;

                        case Constants.LEVEL_NODE:
                            int i = obj.getElementInfoService().addFavoriteNode(tableName, name);
                            if (i <= 0) {
                                msg += "Add node is failed.";
                            }
                            break;

                        default:
                            msg += "Level has error.";
                            break;

                    }
                }catch (Exception e){
                    msg += "Updata has error:" + e.getMessage();
                }
            }
        }

        if (msg.length() == 0){
            result.put("result", Constants.SUCCESS);
            result.put("msg", Constants.MSG_ADD_OK);

        }else{
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_ADD_FAILED + msg);
        }
        return result;
    }
    @POST
    @Path("/suppliers/{supplier}/generations/{generation}/nets/favorites/upload")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject protectImport(@RequestParam(value = "importJson") JSONArray importJson,
                                    @PathParam("supplier") String supplier,
                                    @PathParam("generation") String generation,
                                    @HeaderParam("Auth-Token") String authToken,
                                    @HeaderParam("loginName") String loginName) {

        JSONObject result = new JSONObject();
        String msg = "";
        int addnum = 0;
        String tableNameBase = "unicom_favorite_"+generation+"_";
        String gettableName = "%"+tableNameBase+loginName+"%";
        String tableName = tableNameBase+loginName;
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        if (obj == null){
            msg += "Supplier or Generation has error.";
        }else {
            JSONObject table = obj.getElementInfoService().getTable(gettableName);
            Boolean tableExist = false;

            if (table == null || table.size() ==0 || table.isEmpty()) {
                int j = obj.getElementInfoService().createTable(tableName);
                if (j <= 0) {
                    msg += "Create table failed.";
                }
            } else {
                tableExist = true;
            }
            if (tableExist) {
                List<String> paramList = new ArrayList<>();
                for (int i = 0; i < importJson.size(); i++) {
                    if (importJson.getJSONObject(i).getString("nodeName") == null || importJson.getJSONObject(i).getString("nodeName").isEmpty()){
                        msg +="NodeName is null.";
                    }else {
                        paramList.add(importJson.getJSONObject(i).getString("nodeName"));
                    }
                }
                try {
                    addnum = obj.getElementInfoService().addFavoriteFile(tableName, paramList);
                }catch (Exception e){
                    msg +="AddFavoriteFile is Error." +e.getMessage();
                }
            }

        }
        if (msg.length() != 0) {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA + msg);

        } else {
            result.put("result", Constants.SUCCESS);
            result.put("msg", Constants.MSG_ADD_OK+"(Real/Total:" + addnum + ")");
        }
        return result;
    }

}