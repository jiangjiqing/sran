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
            msg += "Supplier or Generation has error.";

        }else if (groupName == null || groupName.length() == 0){
                msg +="GroupName is null.";

        }else {
            try {
                groupInfo = obj.getElementInfoService().getGroupInfo(groupName);

                if (groupInfo == null || groupInfo.isEmpty()) {
                    msg += "GetGroupInfo is Failed.";
                }
            }catch (Exception e){
                msg += "GroupName has error:" + e.getMessage();
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
            msg += "Supplier or Generation has error.";

        }else if (nodeName == null || nodeName.length() == 0){
                msg +="NodeName is null.";

        }else {
            try {
                nodeInfo = obj.getElementInfoService().getNodeInfo(nodeName);
                if (nodeInfo == null || nodeInfo.isEmpty()) {
                    msg +="GetNodeInfo is Failed.";
                }
            }catch (Exception e){
                msg += "NodeName has error:" + e.getMessage();
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
            msg += "Supplier or Generation has error.";

        }else if (cellName == null || cellName.length() == 0){
                msg +="NodeName is null.";

        }else {
            try {
                cellInfo = obj.getElementInfoService().getCellInfo(cellName);
                if (cellInfo == null || cellInfo.isEmpty()) {
                    msg += "GetCellInfo is Failed.";
                }
            }catch (Exception e){
                msg += "CellName has error:" + e.getMessage();
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
            msg += "Supplier or Generation has error.";

        }else {
            try {
                switch (level) {
                    case Constants.LEVEL_GROUP:
                        infoList = obj.getElementInfoService().getGroupInfoList();
                        break;

                    case Constants.LEVEL_NODE:
                        infoList = obj.getElementInfoService().getNodeInfoList();
                        break;

                    case Constants.LEVEL_CELL:
                        infoList = obj.getElementInfoService().getCellInfoList();
                        break;

                    default:
                        msg += "Level has error.";
                        break;
                }

            }catch (Exception e){
                msg += "InfoList has error:" + e.getMessage();
            }
        }

        if (infoList == null || infoList.isEmpty() || msg.length() != 0) {
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
        List<String> infoList = new ArrayList<>();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        if (obj == null){
            msg += "Supplier or Generation has error.";

        }else {
            try{
                switch (level) {
                    case Constants.LEVEL_GROUP:
                        infoList = obj.getElementInfoService().getGroupNameList();
                        break;

                    case Constants.LEVEL_NODE:
                        infoList = obj.getElementInfoService().getNodeNameList();
                        break;

                    case Constants.LEVEL_CELL:
                        infoList = obj.getElementInfoService().getCellNameList();
                        break;

                    default:
                        msg += "Level has error.";
                        break;
                }
            }catch (Exception e){
                msg += "InfoList has error:" + e.getMessage();
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
            msg += "Supplier or Generation has error.";

        }else {
            try {
                ossNameList = obj.getElementInfoService().getOssNameList();

                if (ossNameList == null || ossNameList.isEmpty() || ossNameList.size() == 0) {
                    msg += "GetOssNameList is null.";
                }
            }catch (Exception e){
                msg += "List has error:" + e.getMessage();
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
        List<JSONObject> groupList = new ArrayList();
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        if (obj == null){
            msg += "Supplier or Generation has error.";

        }else {
            try {
                groupList = obj.getElementInfoService().getGroupInfoList();
                if (groupList == null || groupList.isEmpty() || groupList.size() == 0){
                    msg += "GroupInfoList is null.";
                }
            }catch (Exception e){
                msg += "GroupInfoList has error:" + e.getMessage();
            }
        }

        if (msg.length() == 0){
            result.put("result", Constants.SUCCESS);
            result.put("data", groupList);
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
            msg += "Supplier or Generation has error.";

        }else if (importJson == null || importJson.isEmpty() || importJson.size() == 0) {
            msg += "ImportJson is Null.";

        }else {
            try {
                Integer num = 0;
                for (int i = 0; i < importJson.size(); i++) {

                    if (importJson.getJSONObject(i).getString("nodeName")!=null
                            &&!importJson.getJSONObject(i).getString("nodeName").equals("")) {

                        num += obj.getElementInfoService().updateNode(importJson.getJSONObject(i));
                    }
                }
                if (num == 0) {
                    msg += "UpdateNode is Failed.";
                }
            }catch (Exception e){
                msg += "updateNode has error:" + e.getMessage();
            }
        }

        if (msg.length() == 0){
            result.put("result", Constants.SUCCESS);
            result.put("msg", Constants.MSG_UPLOAD_OK);

        } else {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_UPLOAD_FAILED + msg);
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
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        if (obj == null){
            msg += "Supplier or Generation has error.";

        }else {
            try {
                Integer addNum = 0;
                Integer oldNum = obj.getElementInfoService().getGroupCounter();
                Integer delNum = obj.getElementInfoService().deleteGroup();

                if (oldNum > importJson.size()) {
                    msg += "Import data less than current data.";

                }

                for (int i = 0; i < importJson.size(); i++) {
                    try {
                        if(importJson.getJSONObject(i).getString("groupName")!=null
                                &&!importJson.getJSONObject(i).getString("groupName").equals("")){
                            addNum += obj.getElementInfoService().addRnc(importJson.getJSONObject(i));
                        }


                    } catch (Exception e) {
                        msg1 += "DB has error:" + e.getMessage();
                    }
                }
                if (addNum == null || addNum <= 0) {
                    msg += "Import data(" + importJson.size() + ") less than current data(" + oldNum + ").";

                }else if (addNum < importJson.size()){
                    msg += "Add data(" + addNum + ") less than import data(" + importJson.size() + ").";
                }
            }catch (Exception e){
                msg += "Get old data num or Clear old data has error:" + e.getMessage();
            }
        }

        if(msg.equals("")){
            result.put("result",Constants.SUCCESS);
            result.put("msg",Constants.MSG_ADD_OK);

        }else{
            result.put("result",Constants.FAIL);
            result.put("msg",Constants.MSG_ADD_FAILED + msg);
        }

        return result;
    }
}
