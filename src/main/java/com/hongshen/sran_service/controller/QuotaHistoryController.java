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
import java.util.*;

/**
 * Created by poplar on 11/13/17.
 */
@Path("/sran/service/net/history")
public class QuotaHistoryController extends BaseController {
    @Autowired
    private NetObjFactory objFactory;

    @POST
    @Path("/suppliers/{supplier}/generations/{generation}/nets/{level}/history/quotas")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject quotaHistory(@RequestParam(value = "quotaHistory") JSONObject quotaHistory,
                                   @PathParam("supplier") String supplier, @PathParam("generation") String generation,
                                   @HeaderParam("Auth-Token") String authToken, @PathParam("level") String level) {
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        String formula = quotaHistory.getJSONObject("quota").getString("list").replace("]", "")
                .replace("[", "").replaceAll("\"", "");
        String unit = quotaHistory.getJSONObject("time").getString("unit");
        Date start = quotaHistory.getJSONObject("time").getDate("start");
        Date end = quotaHistory.getJSONObject("time").getDate("end");

        List<JSONObject> quotaList = obj.getQuotaService().getQuotas(start, end);

        JSONObject result = new JSONObject();
        List list = new ArrayList();
        int min = 0;
        int Stutas = 0;
        if (unit.equals("0") && quotaList != null) {//min
            Stutas = 2;
        } else if (unit.equals("1") && quotaList != null) {//hh
            Stutas = 1;
            min = 1;
        } else if (unit.equals("2") && quotaList != null) {//date
            Stutas = 1;
            min = 2;
        } else if (unit.equals("3") && quotaList != null) {//month
            Stutas = 1;
            min = 3;
        }

        if (min != 0 || Stutas != 0) {
            list = getValue(start, end, quotaList,formula, Stutas,min);
            result.put("result", Constants.SUCCESS);
            result.put("date", list);
        } else {
            result.put("result", Constants.FAIL);
        }
        return result;
    }

    public static long getDatePoor(Date endDate, Date nowDate,String msg) {
        long pramary = 0;
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
       // long min = diff % nd % nh / nm;
        // System.out.println(day + "天" + hour + "小时" + min + "分钟");


        if(msg.equals("day")){
            pramary = day;
        }else if(msg.equals("HH")){
            pramary = day*24+hour;
        }
            return pramary;
    }

    public static List getValue(Date start, Date end, List<JSONObject> quotaList,  String formula, int Stutas,int min) {
        List list = new ArrayList();
        SimpleDateFormat MM = new SimpleDateFormat("MM");
        SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");
        int endYaer = Integer.parseInt(yyyy.format(end));
        int startYaer = Integer.parseInt(yyyy.format(start));
        int endMonth = Integer.parseInt(MM.format(end));
        int startMonth =Integer.parseInt(MM.format(start));

        if (Stutas == 1) {
            long index =0;
                if(min ==3){
                    index =(endYaer-startYaer)*12+ endMonth - startMonth;
                }else if(min ==2){
                    index = getDatePoor(end,start,"day");
                }else if(min ==1){
                    index = getDatePoor(end,start,"HH");
                }

            for (int j = 0; j <= index; j++) {

                JSONObject result1 = new JSONObject();
                JSONObject result = new JSONObject();
                List list1 = new ArrayList();
                int num = 0;
                double d = 0.0;
                JSONObject Time = new JSONObject();
                for (JSONObject quolist : quotaList) {

                    Long time = quolist.getDate("time").getTime();

                    if (time >= getMath(start, j,min).getTime()
                            && time < getMath(start, j + 1,min).getTime() && time <= end.getTime()) {
                        if (quolist.getDouble(formula) != null
                                && quolist.getDouble(formula) > 0) {
                            Time.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getMath(start, j,min)));
                            num++;
                            System.out.println(quolist.getDate("time") );
                            d = d + quolist.getDouble(formula);
                        }
                    }
                }
                if (d != 0.0) {
                    result1.put("value", d / num);
                    result1.put("quotaName", formula);
                    list1.add(result1);
                    result.putAll(Time);
                    result.put("quotas", list1);
                    list.add(result);
                }
            }
        } else if (Stutas == 2) {
            JSONObject json = new JSONObject();
            double s = 0.0;
            int num = 0;
            for (JSONObject quolist : quotaList) {

                if (json.keySet().contains(quolist.getString("time")) || s == 0.0) {
                    num++;
                    s = s + quolist.getDouble("formula1");
                    json.put(quolist.getString("time"), s / num);


                } else if (!json.keySet().contains(quolist.getString("time"))) {
                    num = 1;
                    s = quolist.getDouble("formula1");
                    json.put(quolist.getString("time"), s / num);
                }
            }

            for (Object str : json.keySet()) {
                JSONObject result = new JSONObject();
                JSONObject result1 = new JSONObject();
                List list1 = new ArrayList();
                result1.put("value", json.getString(str.toString()));
                result1.put("quotaName", formula);
                list1.add(result1);
                result.put("time", str);
                result.put("quotas", list1);
                list.add(result);
            }
        }
        return list;
    }

    public static Date getMath(Date satrtDate,int num,int min){

        Calendar cal = Calendar.getInstance();
        cal.setTime(satrtDate);//设置起时间
        if(min == 1){
            cal.add(Calendar.HOUR,num);//HH
        }else if(min == 2){
            cal.add(Calendar.DATE,num);//day
        }else if(min == 3){
            cal.add(Calendar.MONTH, num);//增加一个月
        }
        return cal.getTime();
    }
}
