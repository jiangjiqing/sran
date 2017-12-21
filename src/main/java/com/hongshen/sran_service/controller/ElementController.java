package com.hongshen.sran_service.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.service.util.Constants;
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
@Path("/sran/service/net/info")
public class ElementController {

    @Autowired
    private NetObjFactory objFactory;

    // Query specified group info
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupName}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getGroupInfo(@PathParam("supplier")String supplier,
                                   @PathParam("generation")String generation,
                                   @PathParam("groupName")String groupName,
                                   @HeaderParam("Auth-Token")String authToken) {

        String msg ="";
        JSONObject groupInfo = null;
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        if (obj == null){
            msg +="Supplier or Generation is null.";
        }else {
            if (groupName == null){
                msg +="GroupName is null.";
            }else {
                try {
                    groupInfo = obj.getElementInfoService().getGroupInfo(groupName);

                    if (groupInfo == null || groupInfo.isEmpty()) {
                        msg += "GetGroupInfo is Failed.";
                    }
                }catch (Exception e){
                    msg += "GroupName is Error.";
                }

            }
        }
        if (msg.length() == 0){
            result.put("result", Constants.SUCCESS);
            result.put("data", groupInfo);
        } else {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA + msg);
        }
        return result;
    }

    // Query specified node info
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/nodes/{nodeName}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getNodeInfo(@PathParam("supplier")String supplier,
                                  @PathParam("generation")String generation,
                                  @PathParam("nodeName")String nodeName,
                                  @HeaderParam("Auth-Token")String authToken) {
        String msg ="";
        JSONObject nodeInfo = null;
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        if (obj == null){
            msg +="Supplier or Generation is null.";
        }else {
            if (nodeName == null){
                msg +="NodeName is null.";
            }else {
                try {
                    nodeInfo = obj.getElementInfoService().getNodeInfo(nodeName);
                    if (nodeInfo == null || nodeInfo.isEmpty()) {
                        msg +="GetNodeInfo is Failed.";
                    }
                }catch (Exception e){
                    msg += "NodeName is Error.";
                }
            }
        }
        if (msg.length() == 0){
            result.put("result", Constants.SUCCESS);
            result.put("data", nodeInfo);
        } else {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA + msg);
        }
        return result;
    }

    // Query specified cell info
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/cells/{cellName}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getCellInfo(@PathParam("supplier")String supplier,
                                  @PathParam("generation")String generation,
                                  @PathParam("cellName")String cellName,
                                  @HeaderParam("Auth-Token")String authToken) {

        String msg ="";
        JSONObject cellInfo = null;
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        if (obj == null){
            msg +="Supplier or Generation is null.";
        }else {
            if (cellName == null){
                msg +="NodeName is null.";
            }else {
                try {
                    cellInfo = obj.getElementInfoService().getCellInfo(cellName);
                    if (cellInfo == null || cellInfo.isEmpty()) {
                        msg += "GetCellInfo is Failed.";
                    }
                }catch (Exception e){
                    msg += "CellName is Error.";
                }
            }
        }
        if (msg.length() == 0){
            result.put("result", Constants.SUCCESS);
            result.put("data", cellInfo);
        } else {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA + msg);
        }
        return result;
    }

    // Query group info list
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/{level}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getGroupInfoList(@PathParam("supplier")String supplier,
                                       @PathParam("generation")String generation,
                                       @PathParam("level")String level,
                                       @HeaderParam("Auth-Token")String authToken) {

        String msg = "";
        List<JSONObject> infoList = new ArrayList<>();
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        if (obj == null){
            msg +="Supplier or Generation is null.";
        }else {
            switch (level) {
                case "groups":
                    infoList = obj.getElementInfoService().getGroupInfoList();
                    break;

                case "nodes":
                    infoList = obj.getElementInfoService().getNodeInfoList();
                    break;

                case "cells":
                    infoList = obj.getElementInfoService().getCellInfoList();
                    break;

                default:
                    break;
            }
        }

        if (infoList == null || infoList.isEmpty() || msg.length() == 0) {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA + msg);

        } else {
            result.put("result", Constants.SUCCESS);
            result.put("data", infoList);
        }

        return result;
    }

    // Query net elements name list
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/{level}/namelist")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getOSSList(@PathParam("supplier")String supplier,
                                 @PathParam("generation")String generation,
                                 @PathParam("level")String level,
                                 @HeaderParam("Auth-Token")String authToken) {

        String msg = "";
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        List<String> infoList = new ArrayList<>();
        if (obj == null){
            msg +="Supplier or Generation is null.";
        }else {
            switch (level) {
                case "groups":
                    infoList = obj.getElementInfoService().getGroupNameList();
                    break;

                case "nodes":
                    infoList = obj.getElementInfoService().getNodeNameList();
                    break;

                case "cells":
                    infoList = obj.getElementInfoService().getCellNameList();
                    break;

                default:
                    break;
            }
        }
        if (infoList == null || infoList.isEmpty() || msg.length() != 0) {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA);

        } else {
            result.put("result", Constants.SUCCESS);
            result.put("data", infoList);
        }

        return result;
    }

    // Query oss list (3G)
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/osslist")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getOSSList(@PathParam("supplier")String supplier,
                                 @PathParam("generation")String generation,
                                 @HeaderParam("Auth-Token")String authToken) {

        String msg = "";
        List<String> ossNameList = new ArrayList<>();
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        if (obj == null){
            msg +="Supplier or Generation is null.";
        }else {
             ossNameList = obj.getElementInfoService().getOssNameList();

            if (ossNameList == null || ossNameList.isEmpty()) {
                msg += "GetOssNameList is Failed";
            }
        }
        if (msg.length() == 0){
            result.put("result", Constants.SUCCESS);
            result.put("data", ossNameList);
        } else {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA + msg);
        }
        return result;
    }


    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/download")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject groupExport(@PathParam("supplier") String supplier,
                                   @PathParam("generation") String generation,
                                   @HeaderParam("Auth-Token") String authToken) {
        String msg = "";
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        List list = new ArrayList();
        JSONObject result = new JSONObject();

        if (obj == null){
            msg +="Supplier or Generation is null.";
        }else {

            List<JSONObject> groupList = obj.getElementInfoService().getGroupInfoList();
            if (groupList != null && groupList.size() > 0) {


                for (JSONObject group : groupList) {

                    list.add(group);
                }


            } else {

                msg +="GetGroupInfoList is Failed.";
            }
        }
        if (msg.length() == 0){
            result.put("result", Constants.SUCCESS);
            result.put("data", list);
        } else {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA + msg);
        }
        return result;
    }


    @PUT
    @Path("/suppliers/{supplier}/generations/{generation}/nets/nodes/upload")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject nodesImport(@RequestParam(value = "importJson") JSONArray importJson,
                                  @PathParam("supplier") String supplier,
                                  @PathParam("generation") String generation,
                                  @HeaderParam("Auth-Token") String authToken) {

        String msg = "";
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        JSONObject result = new JSONObject();
        if (obj == null){
            msg +="Supplier or Generation is null.";
        }else {
            if (importJson == null) {
                msg +="ImportJson is Null.";
            } else {
                Integer Num = 0;
                for (int i = 0; i < importJson.size(); i++) {

                    Num = obj.getElementInfoService().updateNode(importJson.getJSONObject(i));
                }
                if (Num == 0) {

                    msg +="UpdateNode is Failed.";
                }
            }
        }
        if (msg.length() == 0){
            result.put("result", Constants.SUCCESS);
            result.put("msg", Constants.UPLOAD_RNC_INFOS_OK);
        } else {
            result.put("result", Constants.FAIL);
                result.put("msg", Constants.UPLOAD_RNC_INFOS_FAILED + msg);
        }
        return result;
    }

    @PUT
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/upload")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject rncImport(@RequestParam(value = "importJson") JSONArray importJson,
                                @PathParam("supplier") String supplier,
                                @PathParam("generation") String generation,
                                @HeaderParam("Auth-Token") String authToken) {
        String msg = "";
        String msg1 = "";
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        JSONObject result = new JSONObject();
        if (obj == null){
            msg +="Supplier or Generation is null.";
        }else {
            Integer addNum = 0;
            Integer counter = obj.getElementInfoService().getGroupCounter();
            Integer delNum = obj.getElementInfoService().deleteGroup();

            for (int i = 0; i < importJson.size(); i++) {

                try {

                    addNum = obj.getElementInfoService().addRnc(importJson.getJSONObject(i));

                    if (addNum != null || addNum > 0) {

                        if (counter > importJson.size()) {

                            msg = " and the data of the file is less than the table data";

                        } else {

                        }
                    }
                } catch (Exception e) {

                    msg1 = "DB Exception";
                }
            }
        }
            if(msg.equals("")){

                result.put("result",Constants.SUCCESS);
                result.put("msg",Constants.MSG_ADD_OK);

            }else if(!msg.equals("")){

                result.put("result",Constants.SUCCESS);
                result.put("msg",Constants.MSG_ADD_OK+msg);

            }else if(!msg1.equals("")){

                result.put("result",Constants.FAIL);
                result.put("msg",Constants.MSG_ADD_FAILED+msg1);

            }
        return result;
    }
}
