package com.hongshen.sran_service.controller;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.common.BaseController;
import com.hongshen.sran_service.service.util.Constants;
import com.hongshen.sran_service.service.util.NetObjBase;
import com.hongshen.sran_service.service.util.NetObjFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/**
 * Created by poplar on 11/14/17.
 */
@Path("/sran/service/net/topology")
public class TopologyController extends BaseController {

    @Autowired
    private NetObjFactory objFactory;

    // Query room location list
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/roomlocations")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getRoomLocationList(@PathParam("supplier")String supplier,
                                      @PathParam("generation")String generation,
                                      @HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();

        NetObjBase obj = objFactory.getNetObj(supplier,generation);
        List<JSONObject> locationList = obj.getTelecomRoomService().getRoomLocationList();

        if (locationList.isEmpty()){
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA);

        } else {
            result.put("result", Constants.SUCCESS);
            result.put("data", locationList);
        }

        return result;
    }

    // Query group list by room
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/rooms/{roomName}/groups")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getGroupList(@PathParam("supplier")String supplier,
                                   @PathParam("generation")String generation,
                                   @PathParam("roomName")String roomName,
                                   @HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();

        NetObjBase obj = objFactory.getNetObj(supplier,generation);
        List<String> groupNameList = obj.getTelecomRoomService().getGroupNameListByRoom(roomName);
        //TODO
        if (groupNameList.isEmpty()){
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA);

        } else {
            result.put("result", Constants.SUCCESS);
            result.put("data", groupNameList);
        }

        return result;
    }

    // Query room list
    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/rooms")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getRoomList(@PathParam("supplier")String supplier,
                                   @PathParam("generation")String generation,
                                   @HeaderParam("Auth-Token")String authToken){

        JSONObject result = new JSONObject();

        NetObjBase obj = objFactory.getNetObj(supplier,generation);
        List<JSONObject> roomList = obj.getTelecomRoomService().getRoomList();

        if (roomList.isEmpty()){
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA);

        } else {
            result.put("result", Constants.SUCCESS);
            result.put("data", roomList);
        }

        return result;
    }
}
