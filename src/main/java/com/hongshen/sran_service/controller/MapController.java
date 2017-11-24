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
    public JSONObject  getGroupList(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                         @HeaderParam("Auth-Token")String authToken) {
        JSONObject result = new JSONObject();
        /*if (check(url, method, authToken)) {*/
            NetObjBase obj = objFactory.getNetObj(supplier, generation);
            List<JSONObject> grupList = obj.getElementInfoService().getGroupList();
            List<JSONObject> List =new ArrayList<>();
            for (int i = 0;i<grupList.size();i++){
                JSONObject result1 = new JSONObject();
                List<Double[]> list = new ArrayList<>();

                if (grupList.get(i).getString("group_name") != null) {
                    String groupName= grupList.get(i).getString("group_name");
                    List<JSONObject> nodeList =  obj.getElementInfoService().getNodeList(groupName);

                    JSONObject group = obj.getElementInfoService().getGroupByName(groupName);//TODO
                    if (nodeList.size() != 0) {
                        for (int j = 0; j < nodeList.size(); j++) {

                            if(nodeList.get(j).getDouble("latitude")!= null && nodeList.get(j).getDouble("longitude")!= null){
                                Double[] doubles ={nodeList.get(j).getDouble("latitude"),nodeList.get(j).getDouble("longitude")};
                                list.add(doubles);
                            }
                        }
                    }
                    if(group==null){
                       result1.put("level",-1);
                    }else{
                        result1.putAll(group);
                    }
                    result1.put("name",groupName);
                    result1.putAll(LatitudeAndLongitude(list));
                    List.add(result1);
                }
            }
            result.put("data", List);
            result.put("result", Constants.SUCCESS);
            return result;
       /* }else{
                result.put("result", Constants.FAIL);
			  result.put("msg", Constants.MSG_NO_PERMISSION);
           return result;
        }*/
    }

    static JSONObject Scopes(Double latitude,Double longitude,int num){
        DecimalFormat df   = new DecimalFormat("######0.0000");
        String la = df.format(latitude/num);
        String lo = df.format(longitude/num);
        JSONObject j = new JSONObject();
        if(latitude==0.0||longitude==0.0||num==0){
            j.put("latitude",0);
            j.put("longitude",0);
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
    public JSONObject  getNodeList(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                    @HeaderParam("Auth-Token")String authToken,@PathParam("groupName")String groupName) {

        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        List<JSONObject> nodeList = obj.getElementInfoService().getNodeList(groupName);
        List list = new ArrayList();
        if(!nodeList.isEmpty()){
            for(int i=0;i<nodeList.size();i++){
                JSONObject json = new JSONObject();
                JSONObject nodeLevel = obj.getQuotaService().getNodeLevel(nodeList.get(i).getString("name"));
                if(nodeLevel!=null){
                    json.put("level",nodeLevel.getString("level"));
                }else{
                   json.put("level",-1);
                }
                json.putAll(nodeList.get(i));
                list.add(json);
            }
            result.put("data",list);
            result.put("result", Constants.SUCCESS);
        }else{
            result.put("result",Constants.FAIL);
        }
        return result;
    }

    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/groups/{groupName}/nodes/{nodeName}/cells/mapinfos")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject  getCellList(@PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                   @HeaderParam("Auth-Token")String authToken,@PathParam("groupName")String groupName,
                                   @PathParam("nodeName")String nodeName) {
        JSONObject result = new JSONObject();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        List<JSONObject> cellList = obj.getElementInfoService().getCellList(nodeName);
        if(!cellList.isEmpty()) {
            List list = new ArrayList();

            for (int i=0;i<cellList.size();i++ ){
            JSONObject json = new JSONObject();
            JSONObject cellLevel = obj.getQuotaService().getCellLevel(cellList.get(i).getString("name"));

            JSONObject NodelatAndlong = obj.getElementInfoService().getNodelatitudeAndlongitude(nodeName);
            if(cellLevel!=null){
                json.putAll(cellLevel);
            }else{
                json.put("level",-1);
            }
                json.putAll(NodelatAndlong);
                json.putAll(cellList.get(i));
                list.add(json);
            }
            result.put("data",list);
            result.put("result", Constants.SUCCESS);
        }else{
            result.put("result", Constants.FAIL);
        }

        return  result;
    }
}
