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
    public JSONObject getSpecifiedGroupList(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                        @PathParam("groupName")String groupName, @HeaderParam("Auth-Token")String authToken) {
        JSONObject result = new JSONObject();
        String url = Constants.PATH_DUMMY;
        String method = Constants.METHOD_GET;
//        if (check(url, method, authToken)) {

            NetObjBase obj = objFactory.getNetObj(supplier, generation);

            List<JSONObject> GroupList = obj.getElementInfoService().getSpecifiedGroupList(groupName);

            if (!GroupList.isEmpty()){

                result.put("data", GroupList);
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
    public JSONObject getSpecifiedNodeList(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                            @PathParam("nodeName")String nodeName, @HeaderParam("Auth-Token")String authToken) {
        JSONObject result = new JSONObject();
        String url = Constants.PATH_DUMMY;
        String method = Constants.METHOD_GET;
//        if (check(url, method, authToken)) {
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        List<JSONObject> NodeList = obj.getElementInfoService().getSpecifiedNodeList(nodeName);

        if (!NodeList.isEmpty()){

            result.put("data", NodeList);
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
    public JSONObject getSpecifiedCellList(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                           @PathParam("cellName")String cellName,@HeaderParam("Auth-Token")String authToken) {
        JSONObject result = new JSONObject();
        String url = Constants.PATH_DUMMY;
        String method = Constants.METHOD_GET;
//        if (check(url, method, authToken)) {
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        List<JSONObject> CellList = obj.getElementInfoService().getSpecifiedCellList(cellName);

        if (!CellList.isEmpty()){

            result.put("data", CellList);
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
//    Query group 3G infoList
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

            List<JSONObject> GroupInfoList = obj.getElementInfoService().getGroupInfoList();

            if (!GroupInfoList.isEmpty()) {

                result.put("data", GroupInfoList);
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
}
