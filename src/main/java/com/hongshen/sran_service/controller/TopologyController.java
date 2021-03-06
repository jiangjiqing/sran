package com.hongshen.sran_service.controller;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.common.BaseController;
import com.hongshen.sran_service.service.util.Constants;
import com.hongshen.sran_service.service.util.MapHelper;
import com.hongshen.sran_service.service.util.NetObjBase;
import com.hongshen.sran_service.service.util.NetObjFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
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

        String msg ="";
        JSONObject result = new JSONObject();
        List<JSONObject> locationList = new ArrayList<>();
        NetObjBase obj = objFactory.getNetObj(supplier,generation);
        if (obj == null){
            msg += "Supplier or Generation has error.";

        }else {
            locationList = obj.getTelecomRoomService().getRoomLocationList();
        }
        if (locationList == null || locationList.isEmpty() || msg.length() != 0){
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
    @Path("/suppliers/{supplier}/generations/{generation}/nets/rooms/one/groups")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getGroupList(@PathParam("supplier")String supplier,
                                   @PathParam("generation")String generation,
                                   @HeaderParam("Auth-Token")String authToken,
                                   @QueryParam("roomName") String roomName){

        String msg ="";
        JSONObject result = new JSONObject();
        List<JSONObject> dataList = new ArrayList<>();

        NetObjBase obj = objFactory.getNetObj(supplier,generation);
        if (obj == null){
            msg += "Supplier or Generation has error.";

        }else if (roomName == null || roomName.isEmpty()){
            msg +="RoomName is Null.";
        }else {
            List<JSONObject> groupList = obj.getTelecomRoomService().getGroupListByRoom(roomName);
            if (groupList == null || groupList.isEmpty()){
                msg += "GroupList is Null.";
            }else {
                for (JSONObject group : groupList) {

                    JSONObject dataOne = new JSONObject();
                    String groupName = group.getString("groupName");

                    if (groupName == null || groupName.trim().length() <= 0) {
                        continue;

                    } else {
                        dataOne.put("groupName", groupName);
                        dataOne.put("infos", group);

                        List<JSONObject> nodeLocationList = obj.getElementInfoService().getNodeLocationsByGroup(groupName);
                        JSONObject location = MapHelper.getGroupLocation(nodeLocationList, "latitude", "longitude");

                        if (location.getDoubleValue("latitude") == 0) {
                            location.put("latitude", Constants.INVALID_VALUE_LOCATION);
                        }

                        if (location.getDoubleValue("longitude") == 0) {
                            location.put("longitude", Constants.INVALID_VALUE_LOCATION);
                        }
                        dataOne.putAll(location);

                        dataList.add(dataOne);
                    }
                }
            }
        }
        if (dataList == null || dataList.isEmpty() || msg.length() != 0){
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA);

        } else {
            result.put("result", Constants.SUCCESS);
            result.put("data", dataList);
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
        String msg ="";
        List<String> roomNameList =new ArrayList<>();
        NetObjBase obj = objFactory.getNetObj(supplier,generation);
        if (obj == null){
            msg += "Supplier or Generation has error.";

        }else {
            roomNameList = obj.getTelecomRoomService().getRoomNameList();
        }
        if (roomNameList == null || roomNameList.isEmpty() || msg.length() != 0){
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA + msg);

        } else {
            result.put("result", Constants.SUCCESS);
            result.put("data", roomNameList);
        }

        return result;
    }
}
