package com.hongshen.sran_service.controller;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.common.BaseController;
import com.hongshen.sran_service.service.util.Constants;
import com.hongshen.sran_service.service.util.NetObjBase;
import com.hongshen.sran_service.service.util.NetObjFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by poplar on 11/13/17.
 */
@Path("/sran/service/net/mapinfo")
public class MapController extends BaseController{

    @Autowired
    private NetObjFactory objFactory;

    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/mapinfos")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject  getGroupList(@PathParam("supplier")String supplier,
                                    @PathParam("generation")String generation,
                                    @HeaderParam("Auth-Token")String authToken) {

        JSONObject result = new JSONObject();
        List<JSONObject> dataList = new ArrayList<>();

        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        List<JSONObject> groupList = obj.getElementInfoService().getGroupList();

        for (JSONObject group : groupList){

            String groupName = group.getString("name");

            if (groupName == "") {
                continue;

            }else{
                JSONObject dataOne = new JSONObject();
                dataOne.put("name",groupName);

                // longitude \ latitude and scope
                List<JSONObject> nodeList =  obj.getElementInfoService().getNodeListByGroup(groupName);
                List<Double[]> list = new ArrayList<>();

                if (nodeList.size() != 0) {
                    for (JSONObject node : nodeList) {

                        Double latitude = node.getDouble("latitude");
                        Double longitude = node.getDouble("longitude");

                        if(latitude!= null && longitude!= null){
                            Double[] doubles ={latitude,longitude};
                            list.add(doubles);
                        }else{
                            Double[] doubles ={Double.parseDouble(Constants.INVALID_VALUE_LOCATION),Double.parseDouble(Constants.INVALID_VALUE_LOCATION)};
                            list.add(doubles);
                        }
                    }
                }
                dataOne.putAll(LatitudeAndLongitude(list));

                // group info
                JSONObject groupInfo = obj.getElementInfoService().getGroupInfo(groupName);
                dataOne.put("infos",groupInfo);

                // level
                JSONObject level = obj.getQuotaService().getGroupLevel(groupName);
                if(level != null && level.getIntValue("level") != -1){
                    dataOne.put("level", level);
                }else{
                    dataOne.put("level",Constants.INVALID_VALUE_LEVEL);
                }

                // favorite TODO
                dataOne.put("favorite",false);

                dataList.add(dataOne);
            }
        }

        if(dataList.isEmpty()){
            result.put("result",Constants.FAIL);
            result.put("msg",Constants.MSG_NO_DATA);

        }else {
            result.put("result", Constants.SUCCESS);
            result.put("data", dataList);
        }

        return result;
    }

    static JSONObject Scopes(Double latitude,Double longitude,int num){
        DecimalFormat df   = new DecimalFormat("######0.0000");
        String la = df.format(latitude/num);
        String lo = df.format(longitude/num);
        JSONObject j = new JSONObject();
        if(latitude==0.0||longitude==0.0||num==0){
            j.put("latitude", Constants.INVALID_VALUE_LOCATION);
            j.put("longitude", Constants.INVALID_VALUE_LOCATION);
        }else{
            j.put("latitude",la);
            j.put("longitude",lo);
        }
        return j;
    }

    static JSONObject LatitudeAndLongitude(List<Double[]>  list){
        Double t1 = 0.0;
        Double l1 = 0.0;
        Double t2 = 0.0;
        Double l2 = 0.0;
        Double t3 = 0.0;
        Double l3 = 0.0;
        Double t4 = 0.0;
        Double l4 = 0.0;

        JSONObject result = new JSONObject();
        List listJson = new ArrayList();
        JSONObject json = new JSONObject();
        if(!list.isEmpty()){
            if(list.size() > 3){

                for (int i=0;i<list.size(); i++){
                    //MAXlatitude
                    if(list.get(i)[0]>l1){
                        l1 = list.get(i)[0];
                        t1 = list.get(i)[1];
                    }
                    //MAXlongitude
                    if(list.get(i)[1]>t2){
                        l2 = list.get(i)[0];
                        t2 = list.get(i)[1];
                        System.out.println(i);
                    }
                    //MINlatitude
                    if(list.get(i)[0]<=l3||l3==0.0){
                        l3 = list.get(i)[0];
                        t3 = list.get(i)[1];
                    }
                    //MINlongitude
                    if(list.get(i)[1]<=t4||t4==0.0){
                        l4 = list.get(i)[0];
                        t4 = list.get(i)[1];
                    }

                }
                Double[] d1 = {l1,t1};
                Double[] d2 = {l2,t2};
                Double[] d3 = {l3,t3};
                Double[] d4 = {l4,t4};
                listJson.add(d1);
                listJson.add(d2);
                listJson.add(d3);
                listJson.add(d4);
                Double latitude = l1+l2+l3+l4;
                Double longitude = t1+t2+t3+t4;
                json = Scopes(latitude,longitude,4);
            }else if(list.size()==3){
                listJson.add(list);

            }else if(list.size() < 3){
                listJson.add(2);
            }
        }

        result.put("scope",listJson);
        result.putAll(json);
        System.out.println(result);
        return result;
    };

    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupName}/nodes/mapinfos")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject  getNodeList(@PathParam("supplier")String supplier,
                                   @PathParam("generation")String generation,
                                   @PathParam("groupName")String groupName,
                                   @HeaderParam("Auth-Token")String authToken) {

        JSONObject result = new JSONObject();
        List dataList = new ArrayList();

        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        List<JSONObject> nodeList = obj.getElementInfoService().getNodeListByGroup(groupName);

        for(JSONObject node : nodeList){

            String nodeName = node.getString("name");

            if (nodeName == "") {
                continue;

            }else{
                JSONObject dataOne = new JSONObject();
                dataOne.putAll(node);

                if (node.getString("latitude") == ""){
                    dataOne.put("latitude",Constants.INVALID_VALUE_LOCATION);
                }
                if (node.getString("longitude") == ""){
                    dataOne.put("longitude",Constants.INVALID_VALUE_LOCATION);
                }

                // infos
                JSONObject nodeInfo = obj.getElementInfoService().getNodeInfo(nodeName);
                dataOne.put("infos",nodeInfo);

                // favorite TODO
                dataOne.put("favorite",false);

                // level
                JSONObject level = obj.getQuotaService().getNodeLevel(nodeName);

                if(level != null && level.getIntValue("level") != -1){
                    dataOne.put("level", level);
                }else{
                    dataOne.put("level",Constants.INVALID_VALUE_LEVEL);
                }

                dataList.add(dataOne);
            }

        }

        if(dataList.isEmpty()){
            result.put("result",Constants.FAIL);
            result.put("msg",Constants.MSG_NO_DATA);

        }else {
            result.put("result", Constants.SUCCESS);
            result.put("data", dataList);
        }

        return result;
    }

    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupName}/nodes/{nodeName}/cells/mapinfos")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject  getCellList(@PathParam("supplier")String supplier,
                                   @PathParam("generation")String generation,
                                   @PathParam("groupName")String groupName,
                                   @PathParam("nodeName")String nodeName,
                                   @HeaderParam("Auth-Token")String authToken) {

        JSONObject result = new JSONObject();
        List dataList = new ArrayList();

        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        List<JSONObject> cellList = obj.getElementInfoService().getCellListByNode(nodeName);

        for (JSONObject cell : cellList){

            String cellName = cell.getString("name");

            if (cellName == "") {
                continue;

            }else{
                JSONObject dataOne = new JSONObject();
                dataOne.putAll(cell);

                // longitude and latitude from node (TODO)
                JSONObject nodeLocation = obj.getElementInfoService().getNodeLocation(nodeName);
                dataOne.putAll(nodeLocation);

                if (nodeLocation.getString("latitude") == ""){
                    dataOne.put("latitude",Constants.INVALID_VALUE_LOCATION);
                }
                if (nodeLocation.getString("longitude") == ""){
                    dataOne.put("longitude",Constants.INVALID_VALUE_LOCATION);
                }

                // level
                JSONObject level = obj.getQuotaService().getCellLevel(cellName);

                if (level != null && level.getIntValue("level") != -1) {
                    dataOne.put("level", level);
                } else {
                    dataOne.put("level", Constants.INVALID_VALUE_LEVEL);
                }

                // infos
                JSONObject cellInfo = obj.getElementInfoService().getCellInfo(cellName);
                dataOne.put("infos",cellInfo);

                dataList.add(dataOne);
            }
        }

        if(dataList.isEmpty()){
            result.put("result",Constants.FAIL);
            result.put("msg",Constants.MSG_NO_DATA);

        }else {
            result.put("result", Constants.SUCCESS);
            result.put("data", dataList);
        }

        return  result;
    }
}
