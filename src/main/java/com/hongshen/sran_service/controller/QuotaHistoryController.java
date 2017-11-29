package com.hongshen.sran_service.controller;
import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.common.BaseController;
import com.hongshen.sran_service.service.util.Constants;
import com.hongshen.sran_service.service.util.NetObjBase;
import com.hongshen.sran_service.service.util.NetObjFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by poplar on 11/13/17.
 */
@Path("/sran/service/net/history")
public class QuotaHistoryController extends BaseController{
    @Autowired
    private NetObjFactory objFactory;
    @POST
    @Path("/suppliers/{supplier}/generations/{generation}/nets/{level}/history/quotas")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject quotaHistory( @RequestParam(value = "quotaHistory") JSONObject quotaHistory,
                                    @PathParam("supplier")String supplier, @PathParam("generation")String generation,
                                    @HeaderParam("Auth-Token")String authToken, @PathParam("level")String level){
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        String formula = quotaHistory.getJSONObject("quota").getString("list").replace("]","")
                .replace("[","").replaceAll("\"","");
        String unit = quotaHistory.getJSONObject("time").getString("unit");
        Date start = quotaHistory.getJSONObject("time").getDate("start");
        Date end = quotaHistory.getJSONObject("time").getDate("end");

        List<JSONObject> quotaList = obj.getQuotaService().getQuotas(start,end);

        JSONObject result = new JSONObject();
        List list = new ArrayList();

        int min = 0;
        if (unit.equals("0")&&quotaList!=null){
             min = 15;
        }else if(unit.equals("1")&&quotaList!=null){
             min = 60*24;
        }else if(unit.equals("2")&&quotaList!=null){
             min = 60*24*30;
        }else if(unit.equals("3")&&quotaList!=null){
             min = 60*24*30*12;
        }

        if(min != 0){
            list =   getValue(start,end,quotaList,min,formula);
            result.put("result",Constants.SUCCESS);
            result.put("date",list);
        }else{
            result.put("result",Constants.FAIL);
        }

        return result;
    }

    public static Double getDatePoor(Date endDate, Date nowDate) {

        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
       // System.out.println(day + "天" + hour + "小时" + min + "分钟");
        Double mins = 0.0;

           mins =day*24.0*60.0+hour*60.0+min;

        return mins;
    }

    public static  List getValue(Date start ,Date end,List<JSONObject> quotaList,int min,String formula){
        List list = new ArrayList();

        double L =Math.ceil(getDatePoor(end,start)/min);
        System.out.println(L);
        for (int i=0;i<L;i++){
            JSONObject result1 = new JSONObject();
            JSONObject result = new JSONObject();
            List list1 = new ArrayList();
            int num = 0;
            double d = 0.0;
            JSONObject Time = new JSONObject();
            for(JSONObject quolist:quotaList){

                Long time =quolist.getDate("time").getTime();

                if(start.getTime()+min*60*1000*i<=time
                        &&time<start.getTime() + min*60*1000*(i+1) &&time<=end.getTime()){

                    if(quolist.getDouble(formula)!=null
                            &&quolist.getDouble(formula)>0){
                      Time.put("time",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(start.getTime()+min*60*1000*i));
                        num++;
                        d = d + quolist.getDouble(formula);
                    }
                }
            }
            if(d!=0.0){
                result1.put("value",d/num);
                result1.put("quotaName",formula);
                list1.add(result1);
                result.putAll(Time);
                result.put("quotas",list1);
                list.add(result);
            }

        }

        return list;
    }

}
