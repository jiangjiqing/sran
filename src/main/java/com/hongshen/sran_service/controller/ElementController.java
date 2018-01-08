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
//        JSONObject infos = null;
        ArrayList<String[]> infosProcessed = null;
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        if (obj == null){
            msg += "Supplier or Generation has error.";

        }else if (groupName == null || groupName.length() == 0){
                msg +="GroupName is null.";

        }else {
            try {
//                infos = obj.getElementInfoService().getGroupInfo(groupName);
//                if (infos == null || infos.isEmpty()) {
//                    msg += "Info is null.";
//                }

                infosProcessed = obj.getElementInfoService().getGroupInfoProcessed(groupName);
                if (infosProcessed == null || infosProcessed.isEmpty()) {
                    msg += "Info is null.";
                }
            }catch (Exception e){
                msg += "Get info has error:" + e.getMessage();
            }
        }

        if (msg.length() == 0){
            result.put("result", Constants.SUCCESS);
//            result.put("data", infos);
            result.put("data", infosProcessed);

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
        //        JSONObject infos = null;
        ArrayList<String[]> infosProcessed = null;
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        if (obj == null){
            msg += "Supplier or Generation has error.";

        }else if (nodeName == null || nodeName.length() == 0){
                msg +="NodeName is null.";

        }else {
            try {
//                infos = obj.getElementInfoService().getNodeInfo(nodeName);
//                if (infos == null || infos.isEmpty()) {
//                    msg +="Info is null.";
//                }
                infosProcessed = obj.getElementInfoService().getNodeInfoProcessed(nodeName);
                if (infosProcessed == null || infosProcessed.isEmpty()) {
                    msg += "Info is null.";
                }
            }catch (Exception e){
                msg += "Get info has error:" + e.getMessage();
            }
        }

        if (msg.length() == 0){
            result.put("result", Constants.SUCCESS);
//            result.put("data", infos);
            result.put("data", infosProcessed);
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
        //        JSONObject infos = null;
        ArrayList<String[]> infosProcessed = null;
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        if (obj == null){
            msg += "Supplier or Generation has error.";

        }else if (cellName == null || cellName.length() == 0){
                msg +="NodeName is null.";

        }else {
            try {
//                infos = obj.getElementInfoService().getCellInfo(cellName);
//                if (infos == null || infos.isEmpty()) {
//                    msg +="Info is null.";
//                }
                infosProcessed = obj.getElementInfoService().getNodeInfoProcessed(cellName);
                if (infosProcessed == null || infosProcessed.isEmpty()) {
                    msg += "Info is null.";
                }
            }catch (Exception e){
                msg += "Get info has error:" + e.getMessage();
            }
        }

        if (msg.length() == 0){
            result.put("result", Constants.SUCCESS);
//            result.put("data", infos);
            result.put("data", infosProcessed);
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
    public JSONObject nodesImport(@RequestParam(value = "param") JSONArray param,
                                  @PathParam("supplier") String supplier,
                                  @PathParam("generation") String generation,
                                  @HeaderParam("Auth-Token") String authToken) {

        String msg = "";
        Integer listNum = 0;
        Integer realAddNum = 0;
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        JSONObject result = new JSONObject();
        if (obj == null){
            msg += "Supplier or Generation has error.\n";

        }else if (param == null || param.isEmpty() || param.size() == 0) {
            msg += "Parametas is Null.\n";

        }else {
            try {
                listNum = param.size();
                for (int i = 0; i < listNum; i++) {

                    if (param.getJSONObject(i).getString("nodeName")!=null
                            &&!param.getJSONObject(i).getString("nodeName").equals("")) {

                        realAddNum += obj.getElementInfoService().updateStationName(param.getJSONObject(i));
                    }
                }

            }catch (Exception e){
                msg += "update has error:" + e.getMessage();
            }
        }

        if (msg.length() == 0){
            result.put("result", Constants.SUCCESS);
            result.put("msg", Constants.MSG_UPLOAD_OK + "(Real/Total:" + realAddNum + "/" + listNum + ")");
        } else {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_UPLOAD_FAILED + "(Real/Total:" + realAddNum + "/" + listNum + ")\n" + msg);
        }

        return result;
    }

    @PUT
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/upload")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject rncImport(@RequestParam(value = "groupList") JSONArray groupList,
                                @PathParam("supplier") String supplier,
                                @PathParam("generation") String generation,
                                @HeaderParam("Auth-Token") String authToken) {
        String msg = "";
        Integer listNum = 0;
        Integer realAddNum = 0;
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        if (obj == null){
            msg += "Supplier or Generation has error.";

        }else if (groupList == null) {
            msg += "GroupList is null.";

        }else{
            try {
                listNum = groupList.size();
                Integer oldListNum = obj.getElementInfoService().getGroupCounter();
                Integer delNum = obj.getElementInfoService().deleteGroup();

                if (oldListNum != delNum) {
                    msg += "Clear data has error. (Total/Clear:" + oldListNum + "/" + delNum + ")";

                }else {
                    for (int i = 0; i < listNum; i++) {
                        try {
                            if (groupList.getJSONObject(i).getString("groupName") != null
                                    && !groupList.getJSONObject(i).getString("groupName").equals("")) {
                                realAddNum += obj.getElementInfoService().addRnc(groupList.getJSONObject(i));
                            }
                        } catch (Exception e) {
                            msg += "Insert data error:" + e.getMessage();
                        }
                    }

                    if (realAddNum == null || realAddNum <= 0) {
                        msg += "Insert no data.";

                    }else if (realAddNum < listNum){
                        msg += "Insert data has error.";
                    }
                }
            }catch (Exception e){
                msg += "Get old data num or Clear old data has error:" + e.getMessage();
            }
        }

        if(msg.equals("")){
            result.put("result",Constants.SUCCESS);
            result.put("msg",Constants.MSG_ADD_OK + "(Real/Total:" + realAddNum + "/" + listNum + ")");

        }else{
            result.put("result",Constants.FAIL);
            result.put("msg",Constants.MSG_ADD_FAILED + "(Real/Total:" + realAddNum + "/" + listNum + ")\n" + msg);
        }

        return result;
    }
}
