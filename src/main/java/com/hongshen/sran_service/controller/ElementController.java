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

        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        JSONObject groupInfo = obj.getElementInfoService().getGroupInfo(groupName);

        if (groupInfo == null || groupInfo.isEmpty()){
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA);

        } else {
            result.put("result", Constants.SUCCESS);
            result.put("data", groupInfo);
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

        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        JSONObject nodeInfo = obj.getElementInfoService().getNodeInfo(nodeName);

        if (nodeInfo == null || nodeInfo.isEmpty()){
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA);

        } else {
            result.put("result", Constants.SUCCESS);
            result.put("data", nodeInfo);
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

        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        JSONObject cellInfo = obj.getElementInfoService().getCellInfo(cellName);

        if (cellInfo == null || cellInfo.isEmpty()){
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA);

        } else {
            result.put("result", Constants.SUCCESS);
            result.put("data", cellInfo);
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

        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        List<JSONObject> infoList = new ArrayList<>();

        switch (level){
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

        if (infoList == null || infoList.isEmpty()) {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA);

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

        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        List<String> infoList = new ArrayList<>();

        switch (level){
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

        if (infoList == null || infoList.isEmpty()) {
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

        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        List<String> ossNameList = obj.getElementInfoService().getOssNameList();

        if (ossNameList == null || ossNameList.isEmpty()) {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA);

        } else {
            result.put("result", Constants.SUCCESS);
            result.put("data", ossNameList);
        }

        return result;
    }


    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/download")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject groupExport(@PathParam("supplier") String supplier,
                                   @PathParam("generation") String generation,
                                   @HeaderParam("Auth-Token") String authToken) {
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        List list = new ArrayList();
        JSONObject result = new JSONObject();

        List<JSONObject> groupList = obj.getElementInfoService().getGroupInfoList();
        if (groupList!=null&&groupList.size()>0) {


            for (JSONObject group : groupList) {

                list.add(group);
            }

            result.put("result",Constants.SUCCESS);
            result.put("data", list);
        }else {

            result.put("result",Constants.FAIL);
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
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        JSONObject result = new JSONObject();
        if(importJson!=null) {
            Integer Num = 0;
            for (int i=0;i<importJson.size();i++) {

                Num = obj.getElementInfoService().updateNode(importJson.getJSONObject(i));
            }
            if(Num!=0){
                result.put("result",Constants.SUCCESS);
                result.put("msg",Constants.UPLOAD_RNC_INFOS_OK);
            }
        }else {
            result.put("result",Constants.FAIL);
            result.put("msg",Constants.UPLOAD_RNC_INFOS_FAILED);
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
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        JSONObject result = new JSONObject();
        Integer addNum = 0;
        Integer counter = obj.getElementInfoService().getGroupCounter();
        Integer delNum = obj.getElementInfoService().deleteGroup();

            for (int i = 0; i < importJson.size(); i++) {

                try {

                    addNum = obj.getElementInfoService().addRnc(importJson.getJSONObject(i));

                    if(addNum!=null||addNum>0){

                        result.put("result",Constants.SUCCESS);

                        if(counter>importJson.size()){

                            result.put("msg",Constants.MSG_ADD_OK
                                    +" and the data of the file is less than the table data");
                        }else {
                            result.put("msg",Constants.MSG_ADD_OK);
                        }

                    }
                }catch (Exception e){

                    result.put("result",Constants.FAIL);
                    result.put("msg","DB Exception");
                }
            }
        return result;
    }
}
