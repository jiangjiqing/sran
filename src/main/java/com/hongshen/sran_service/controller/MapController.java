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

            if (groupName == null || groupName == "") {
                continue;

            }else{
                JSONObject dataOne = new JSONObject();
                dataOne.put("name",groupName);

                // longitude \ latitude and scope
                List<JSONObject> nodeList =  obj.getElementInfoService().getNodeLocationsByGroup(groupName);
                List<Double[]> list = new ArrayList<>();

                    for (JSONObject node : nodeList) {

                        Double latitude = node.getDouble("latitude");
                        Double longitude = node.getDouble("longitude");

                        if(latitude!= null && longitude!= null&&latitude!=0.0&&longitude!=0.0){
                            Double[] doubles ={latitude,longitude};
                            list.add(doubles);
                        }
                }
                dataOne.putAll(LatitudeAndLongitude(list));

                // group info
                JSONObject groupInfo = obj.getElementInfoService().getGroupInfo(groupName);
                dataOne.put("infos",groupInfo);

                // level
                JSONObject level = obj.getQuotaService().getGroupLevel(groupName);
                if(level != null && level.getIntValue("level") != -1){
                    dataOne.putAll(level);
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
                Double latitude = 0.0;
                Double longitude =0.0;
                for (int i=0;i<list.size();i++){
                    latitude = latitude + list.get(i)[0];
                    longitude = longitude + list.get(i)[1];
                }

                listJson.add(list);
                json = Scopes(latitude,longitude,3);
            }else if(list.size() ==2){
                Double[] d1={list.get(0)[0]+0.0001,list.get(0)[1]+0.0001};
                Double[] d2={list.get(1)[0]-0.0001,list.get(1)[1]-0.0001};
                Double[] d3={list.get(0)[0]+0.0001,list.get(1)[1]-0.0001};
                Double[] d4={list.get(1)[0]-0.0001,list.get(0)[1]+0.0001};
                Double latitude = (list.get(0)[0]+list.get(1)[0]);
                Double longitude = (list.get(0)[1]+list.get(1)[1]);

                listJson.add(d1);
                listJson.add(d2);
                listJson.add(d3);
                listJson.add(d4);

                json = Scopes(latitude,longitude,2);
            }else if(list.size() ==1){

                Double[] d1  = {list.get(0)[0]+0.0001,list.get(0)[1]+0.0001};
                Double[] d2  = {list.get(0)[0]-0.0001,list.get(0)[1]-0.0001};
                Double[] d3  = {list.get(0)[0]+0.0001,list.get(0)[1]-0.0001};
                Double[] d4  = {list.get(0)[0]-0.0001,list.get(0)[1]+0.0001};
                listJson.add(d1);
                listJson.add(d2);
                listJson.add(d3);
                listJson.add(d4);
                json = Scopes(list.get(0)[0],list.get(0)[1],1);

            }
        }

        result.put("scope",listJson);
        result.putAll(json);
        System.out.println(result);
        return result;
    }

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

            if (nodeName == null || nodeName == "") {
                continue;

            }else{
                JSONObject dataOne = new JSONObject();
                dataOne.putAll(node);

                String latitude = node.getString("latitude");
                if (latitude == null || latitude == ""){
                    dataOne.put("latitude",Constants.INVALID_VALUE_LOCATION);
                }

                String longitude = node.getString("longitude");
                if (longitude == null || longitude == ""){
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
                    dataOne.putAll(level);
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

            if (cellName == null || cellName == "") {
                continue;

            }else{
                JSONObject dataOne = new JSONObject();
                dataOne.putAll(cell);

                // longitude and latitude from node (TODO : confirm)
                JSONObject nodeLocation = obj.getElementInfoService().getNodeLocation(nodeName);
                dataOne.putAll(nodeLocation);

                String latitude = cell.getString("latitude");
                if (latitude == null || latitude == ""){
                    dataOne.put("latitude",Constants.INVALID_VALUE_LOCATION);
                }

                String longitude = cell.getString("longitude");
                if (longitude == null || longitude == ""){
                    dataOne.put("longitude",Constants.INVALID_VALUE_LOCATION);
                }

                // level
                JSONObject level = obj.getQuotaService().getCellLevel(cellName);

                if (level != null && level.getIntValue("level") != -1) {
                    dataOne.putAll(level);
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
