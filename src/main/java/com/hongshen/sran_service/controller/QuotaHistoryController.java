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
import java.text.ParseException;
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
                                   @PathParam("supplier") String supplier,
                                   @PathParam("generation") String generation,
                                   @PathParam("level") String level,
                                   @HeaderParam("Auth-Token") String authToken) throws ParseException {

            String msg = "";
            JSONObject result = new JSONObject();

            List dataList = new ArrayList();

            String condition = null;
            Date start = null;
            Date end = null;
            List<String> formulaNameList = new ArrayList<>();
            String[] formula = null;
            int min = 0;
            List<JSONObject> quotaList = new ArrayList<>();
            NetObjBase obj = objFactory.getNetObj(supplier, generation);
            if (obj ==null){
                msg +="Supplier or Generation is null.";
            }else {

                String unit = quotaHistory.getJSONObject("time").getString("unit");

                if (quotaHistory.getJSONObject("quota").getString("range").equals("1")) {
                    formula = quotaHistory.getJSONObject("quota").getString("list").replace("]", "")
                            .replace("[", "").replaceAll("\"", "").split(",");

                    for (String str : formula) {

                        formulaNameList.add(str);
                    }

                } else if (quotaHistory.getJSONObject("quota").getString("range").equals("0")) {

                    formulaNameList = obj.getCacheService().getFormulaNameList(false);
                }
                if (quotaHistory.getJSONObject("time").getString("range").equals("1")) {
                    SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    String start1 = quotaHistory.getJSONObject("time").getString("start");
                    String end1 = quotaHistory.getJSONObject("time").getString("end");
                    start = date.parse(start1);
                    end = date.parse(end1);

                }

                if (quotaHistory.getJSONObject("element").getString("range").equals("1")) {

                    String[] st = quotaHistory.getJSONObject("element").getString("list").replace("]", "")
                            .replace("[", "").replaceAll("\"", "").split(",");

                    condition = Condition(st);

                }
                 quotaList = getQuotas(level, obj, start, end, condition);


                if (unit.equals("0") && quotaList != null) {//min

                    min = 4;
                } else if (unit.equals("1") && quotaList != null) {//hh

                    min = 1;
                } else if (unit.equals("2") && quotaList != null) {//date

                    min = 2;
                } else if (unit.equals("3") && quotaList != null) {//month

                    min = 3;
                }


            }
        if (min != 0 && quotaList.size() > 0 || msg.length() != 0) {
            dataList = getValue(start, end, quotaList, formulaNameList, min, quotaList.get(quotaList.size() - 1).getDate("time"), quotaList.get(0).getDate("time"));
            result.put("result", Constants.SUCCESS);
            result.put("data", dataList);

        } else {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA);
        }
        return result;
    }

    private static long getDatePoor(Date endDate, Date nowDate,String msg) {
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
       long mins = diff % nd % nh / nm;

        if(msg.equals("day")){
            pramary = day;
        }else if(msg.equals("HH")){
            pramary = day*24+hour;
        }else if(msg.equals("MIN")){
           pramary=day*24*60+hour*60+mins;
        }
        return pramary;
    }

    private static List getValue(Date start, Date end, List<JSONObject> quotaList,List<String> formulaNameList,int min,Date start1,Date end1) {
        List list = new ArrayList();
        if (start==null||end==null){
            start = start1;
            end = end1;
        }

        SimpleDateFormat MM = new SimpleDateFormat("MM");
        SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");
        int endYaer = Integer.parseInt(yyyy.format(end));
        int startYaer = Integer.parseInt(yyyy.format(start));
        int endMonth = Integer.parseInt(MM.format(end));
        int startMonth =Integer.parseInt(MM.format(start));

            long index =0;
                if(min ==3){
                    index =(endYaer-startYaer)*12+ endMonth - startMonth;
                }else if(min ==2){
                    index = getDatePoor(end,start,"day");
                }else if(min ==1){
                    index = getDatePoor(end,start,"HH");
                }else if(min ==4){
                    index = getDatePoor(end,start,"MIN")/15;
                }

            for (int j = 0; j <= index; j++) {

                JSONObject result = new JSONObject();
                List list1 = new ArrayList();
                int num = 0;
                double d = 0.0;
                JSONObject Time = new JSONObject();

                for (String formulaName : formulaNameList){

                    d = 0.0;
                    num = 0;
                    JSONObject result1 = new JSONObject();

                    for (JSONObject quolist : quotaList) {

                        Long time = quolist.getDate("time").getTime();

                        if (time >= getMath(start, j, min).getTime()
                                && time < getMath(start, j + 1, min).getTime() && time <= end.getTime()) {

                            if (quolist.getDouble(formulaName) != null
                                    && quolist.getDouble(formulaName) > 0) {

                                Time.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getMath(start, j, min)));
                                num++;
                                d = d + quolist.getDouble(formulaName);

                            }
                        }
                    }
                    if (d != 0.0) {
                        result1.put("value", d / num);
                        result1.put("quotaName", formulaName);
                        list1.add(result1);
                        result.putAll(Time);
                    }
                }

               if(list1.size()>0) {
                   result.put("quotas", list1);
                   list.add(result);
               }
            }

        return list;
    }

    private static Date getMath(Date satrtDate,int num,int min){
        Calendar cal = Calendar.getInstance();
        cal.setTime(satrtDate);//设置起时间
        if(min == 1){
            cal.add(Calendar.HOUR,num);//HH
        }else if(min == 2){
            cal.add(Calendar.DATE,num);//day
        }else if(min == 3){
            cal.add(Calendar.MONTH, num);//增加一个月
        }else if(min == 4 ){
            cal.add(Calendar.MINUTE,15*num);
        }
        return cal.getTime();
    }

    private static String Condition(String[] st){
        String name = "";
        String  condition = null;
        if(st.length>0){
            for (int i=0;i<st.length;i++) {
                String and = "";
                if (i!=st.length-1) {
                    and = "||";
                }
                name =name + '"'+st[i]+'"'+ " " + and + " ";
            }

            condition = "where"+" "+"name="+name;
        }

        return condition;

    }

    private static List<JSONObject> getQuotas(String level,NetObjBase obj,Date start,Date end,String condition){
        List<JSONObject> quotaListexport = new ArrayList<JSONObject>();

        switch (level){
            case "groups":
                quotaListexport= obj.getQuotaService().getQuotas(start, end,condition);
                break;
            case "nodes":
                quotaListexport= obj.getQuotaService().getQuotasNode(start, end,condition);
                break;
            case "cells":
                quotaListexport= obj.getQuotaService().getQuotasCell(start, end,condition);
                break;
        }
        return quotaListexport;
    }

    @POST
    @Path("/suppliers/{supplier}/generations/{generation}/nets/{level}/history/quotas/download")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject quotaHistoryExport(@RequestParam(value = "quotaHistory") JSONObject quotaHistoryExport,
                                         @PathParam("supplier") String supplier,
                                         @PathParam("generation") String generation,
                                         @PathParam("level") String level,
                                         @HeaderParam("Auth-Token") String authToken) throws ParseException {

        String msg = "";
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        String condition = null;
        Date start = null;
        Date end = null;
        List<String> formulaNameList = new ArrayList<>();

        if(quotaHistoryExport.getJSONObject("time").getString("range").equals("1")){

            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String startExport = quotaHistoryExport.getJSONObject("time").getString("start");
            String endExport = quotaHistoryExport.getJSONObject("time").getString("end");
            start = date.parse(startExport);
            end = date.parse(endExport);
        }

        if(quotaHistoryExport.getJSONObject("element").getString("range").equals("1")){

            String[] st = quotaHistoryExport.getJSONObject("element").getString("list").replace("]", "")
                    .replace("[", "").replaceAll("\"", "").split(",");

            condition =Condition(st);

        }

        if(quotaHistoryExport.getJSONObject("quota").getString("range").equals("1")){

            formulaNameList = JSONObject.parseArray(quotaHistoryExport.getJSONObject("quota").getString("list"),String.class);

        }else if(quotaHistoryExport.getJSONObject("quota").getString("range").equals("0")){

            formulaNameList = obj.getCacheService().getFormulaNameList(false);
        }

        List<JSONObject> quotaListexport = getQuotas(level,obj,start,end,condition);

        JSONObject result = new JSONObject();

        List list = new ArrayList();
        for (JSONObject export:quotaListexport){
            JSONObject result1 = new JSONObject();
            JSONObject json = new JSONObject();
            result1.put("name",export.getString("name"));
            result1.put("time",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(export.getDate("time")));
            for (String formulaName : formulaNameList){

                json.put(formulaName,export.getString(formulaName));
            }

            result1.putAll(json);
            list.add(result1);
        }

        result.put("data",list);
        return result;
    }

    @POST
    @Path("/suppliers/{supplier}/generations/{generation}/nets/{level}/history/counters/download")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject quotaHistoryCounterExport(@RequestParam(value = "quotaHistory") JSONObject quotaHistoryExport,
                                                @PathParam("supplier") String supplier, @PathParam("generation") String generation,
                                                @HeaderParam("Auth-Token") String authToken, @PathParam("level") String level) throws ParseException {
        NetObjBase obj = objFactory.getNetObj(supplier, generation);
        String condition = null;
        Date start =null;
        Date end = null;
        JSONObject result =new JSONObject();
        List list = new ArrayList();

        if(quotaHistoryExport.getJSONObject("time").getString("range").equals("1")){

            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String startExport = quotaHistoryExport.getJSONObject("time").getString("start");
            String endExport = quotaHistoryExport.getJSONObject("time").getString("end");
            start = date.parse(startExport);
            end = date.parse(endExport);
        }
        if(quotaHistoryExport.getJSONObject("element").getString("range").equals("1")){

            String[] st = quotaHistoryExport.getJSONObject("element").getString("list").replace("]", "")
                    .replace("[", "").replaceAll("\"", "").split(",");

            condition =Condition(st);
        }

        List<JSONObject> counterList = obj.getQuotaService().getCounterExportGroup(start,end,condition);

        for (JSONObject json : counterList) {
            JSONObject result1 =new JSONObject();
            result1.put("name",json.getString("name"));
            result1.put("time",json.getDate("time"));
            for (int i=1;i<=25;i++){
                result1.put("counter"+i,json.getString("counter"+i));
            }

            list.add(result1);

        }
        result.put("data",list);
        return result;

    }
}
