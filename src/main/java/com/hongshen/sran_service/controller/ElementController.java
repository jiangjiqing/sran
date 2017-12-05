package com.hongshen.sran_service.controller;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.service.util.Constants;
import com.hongshen.sran_service.service.util.Httpclient;
import com.hongshen.sran_service.service.util.NetObjBase;
import com.hongshen.sran_service.service.util.NetObjFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by poplar on 11/13/17.
 */
@Path("/sran/service/net/info")
public class ElementController {

    @Autowired
    private NetObjFactory objFactory;

    @Autowired
    private Httpclient httpclient;

//   Query specified group information container
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupName}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getGroupInfo(@PathParam("supplier")String supplier,
                                   @PathParam("generation")String generation,
                                   @PathParam("groupName")String groupName,
                                   @HeaderParam("Auth-Token")String authToken) {
        JSONObject result = new JSONObject();
        String url = Constants.PATH_DUMMY;
        String method = Constants.METHOD_GET;
//        if (check(url, method, authToken)) {

            NetObjBase obj = objFactory.getNetObj(supplier, generation);

            JSONObject groupInfo = obj.getElementInfoService().getGroupInfo(groupName);

            if (!groupInfo.isEmpty()){

                result.put("data", groupInfo);
                result.put("result", Constants.SUCCESS);

            } else {

                result.put("msg", Constants.MSG_NO_DATA);
                result.put("result", Constants.FAIL);

            }
        return result;
        //        } else {
//
//            return result;
//        }
    }

//    Query specified node information container
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/nodes/{nodeName}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getNodeInfo(@PathParam("supplier")String supplier,
                                  @PathParam("generation")String generation,
                                  @PathParam("nodeName")String nodeName,
                                  @HeaderParam("Auth-Token")String authToken) {
        JSONObject result = new JSONObject();
        String url = Constants.PATH_DUMMY;
        String method = Constants.METHOD_GET;
//        if (check(url, method, authToken)) {
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        JSONObject nodeInfo = obj.getElementInfoService().getNodeInfo(nodeName);

        if (!nodeInfo.isEmpty()){

            result.put("data", nodeInfo);
            result.put("result", Constants.SUCCESS);

        } else {

            result.put("msg", Constants.MSG_NO_DATA);
            result.put("result", Constants.FAIL);

        }
        return result;
//        } else {
//
//            return result;
//        }
    }

    //    Query specified cell information container
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/cells/{cellName}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getCellInfo(@PathParam("supplier")String supplier,
                                  @PathParam("generation")String generation,
                                  @PathParam("cellName")String cellName,
                                  @HeaderParam("Auth-Token")String authToken) {
        JSONObject result = new JSONObject();
        String url = Constants.PATH_DUMMY;
        String method = Constants.METHOD_GET;
//        if (check(url, method, authToken)) {
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        JSONObject cellInfo = obj.getElementInfoService().getCellInfo(cellName);

        if (!cellInfo.isEmpty()){

            result.put("data", cellInfo);
            result.put("result", Constants.SUCCESS);

        } else {

            result.put("msg", Constants.MSG_NO_DATA);
            result.put("result", Constants.FAIL);

        }
        return result;
//        } else {
//
//            return result;
//        }
    }

// Query group 3G infoList
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getGroupInfoList(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                           @HeaderParam("Auth-Token")String authToken) {
        JSONObject result = new JSONObject();
        String url = Constants.PATH_DUMMY;
        String method = Constants.METHOD_GET;
//        if (check(url, method, authToken)) {
            NetObjBase obj = objFactory.getNetObj(supplier, generation);

            List<JSONObject> GroupInfoList = obj.getElementInfoService().getGroupList();

            if (GroupInfoList.isEmpty()) {
                result.put("result", Constants.FAIL);
                result.put("msg", Constants.MSG_NO_DATA);

            } else {
                result.put("result", Constants.SUCCESS);
                result.put("data", GroupInfoList);
            }
        return result;

//        } else {
//
//            return result;
//        }
    }
}
