package com.hongshen.sran_service.controller;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.service.util.Constants;
import com.hongshen.sran_service.service.util.Httpclient;
import com.hongshen.sran_service.service.util.NetObjBase;
import com.hongshen.sran_service.service.util.NetObjFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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

        JSONObject result = new JSONObject();
        String tableName = "unicom_favorite_" + generation + "_";
        String gettableName = "%" + tableName + loginName + "%";
        String TableName = tableName + loginName;
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        JSONObject table = obj.getElementInfoService().getTable(gettableName);

        if (table == null) {
            result.put("msg", Constants.MSG_NO_DATA);
            result.put("result", Constants.FAIL);

        }else{

            List<JSONObject> favoriteList = obj.getElementInfoService().getFavoriteList(TableName);

            for (JSONObject node : favoriteList) {

                String nodeName = String.valueOf(node.getString("nodeName"));

                if (nodeName != null || nodeName != "") {

                    JSONObject level = obj.getQuotaService().getNodeLevel(nodeName);

                    if (level != null && level.getIntValue("level") != -1) {

                        node.put("level", level);

                    } else {

                        node.put("level", Constants.INVALID_VALUE_LEVEL);
                    }

                }

            }

            if (favoriteList.isEmpty()) {

                result.put("msg", Constants.MSG_NO_DATA);
                result.put("result", Constants.FAIL);

            } else {

                result.put("data", favoriteList);
                result.put("result", Constants.SUCCESS);

            }
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
        String tableNameBase = "unicom_favorite_"+generation+"_";
        String gettableName = "%"+tableNameBase+loginName+"%";
        String tableName = tableNameBase+loginName;
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        JSONObject table =  obj.getElementInfoService().getTable(gettableName);

        if (table != null){
            if (level.equals("nodes")){
                String nodeName = name;
                int i = obj.getElementInfoService().deleteNode(tableName,nodeName);
                if (i > 0) {
                    System.out.println(i);
                    result.put("msg", Constants.MSG_DELETE_OK);
                    result.put("result", Constants.SUCCESS);
                } else {
                    System.out.println(i);
                    result.put("msg", Constants.MSG_DELETE_FAILED);
                    result.put("result", Constants.FAIL);
                }
            }else if (level.equals("groups")){

                    int i = obj.getElementInfoService().deleteNodes(tableName,name);
                    if (i > 0) {

                        result.put("msg", Constants.MSG_DELETE_OK);
                        result.put("result", Constants.SUCCESS);
                    } else {

                        result.put("msg", Constants.MSG_DELETE_FAILED);
                        result.put("result", Constants.FAIL);
                    }

            }else {
                result.put("msg", Constants.MSG_DELETE_FAILED);
                result.put("result", Constants.FAIL);
            }
        }else {
            result.put("msg", Constants.MSG_DELETE_FAILED);
            result.put("result", Constants.FAIL);
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
        String tableNameBase = "unicom_favorite_"+generation+"_";
        String gettableName = "%"+tableNameBase+loginName+"%";
        String tableName = tableNameBase+loginName;
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        JSONObject table =  obj.getElementInfoService().getTable(gettableName);
        if (table != null){
            if(level.equals("nodes")){
                int i = obj.getElementInfoService().addNode(tableName, name);
                if (i > 0) {
                    result.put("msg", Constants.MSG_ADD_OK);
                    result.put("result", Constants.SUCCESS);
                } else {
                    result.put("msg", Constants.MSG_ADD_FAILED);
                    result.put("result", Constants.FAIL);
                }
            }else if (level.equals("groups")) {
                List<JSONObject> nodeNames = obj.getElementInfoService().getNodeListByGroup(name);
                    if (nodeNames.size() != 0) {
                        int i = obj.getElementInfoService().addNodes(tableName, nodeNames);
                        if (i > 0) {
                            result.put("msg", Constants.MSG_ADD_OK);
                            result.put("result", Constants.SUCCESS);
                        } else {
                            result.put("msg", Constants.MSG_ADD_FAILED);
                            result.put("result", Constants.FAIL);
                        }
                    } else {

                        result.put("msg", Constants.MSG_ADD_FAILED);
                        result.put("result", Constants.FAIL);
                    }
            }else {

                result.put("msg", Constants.MSG_ADD_FAILED);
                result.put("result", Constants.FAIL);
            }
        }else {

            result.put("msg", Constants.MSG_ADD_FAILED);
            result.put("result", Constants.FAIL);
        }
        return result;
    }

}