package com.hongshen.sran_service.controller;
import com.alibaba.fastjson.JSONArray;
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
        int min = 0;
        List<JSONObject> quotaList = new ArrayList<>();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        if (obj ==null){
            msg += "Supplier or Generation is null.";

        }else if (quotaHistory == null || quotaHistory.isEmpty()) {
            msg += "Parameters is null.";

        }else {

            JSONObject quota = quotaHistory.getJSONObject("quota");
            JSONObject time = quotaHistory.getJSONObject("time");
            JSONObject element = quotaHistory.getJSONObject("element");

            if (quota == null || quota.isEmpty() || !quota.containsKey("range") ||
                    time == null || time.isEmpty() ||
                    !time.containsKey("range") || !time.containsKey("unit") ||
                    element == null || element.isEmpty() || !element.containsKey("range")){

                msg += "Parameters has error.";

            }else {
                // quota
                if (quota.getString("range").equals("1")) {
                    JSONArray formula = quota.getJSONArray("list");
                    for (Object f : formula) {
                        formulaNameList.add(f.toString());
                    }

                } else if (quota.getString("range").equals("0")) {
                    formulaNameList = obj.getCacheService().getFormulaNameList(false);
                }

                // time
                if (time.getString("range").equals("1")) {

                    SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    String start1 = time.getString("start");
                    String end1 = time.getString("end");

                    if (start1 == null || start1.length() == 0 ||
                            end1 == null || end1.length() == 0) {
                        msg += "Parameters('time') has error.";

                    } else {
                        start = date.parse(start1);
                        end = date.parse(end1);
                    }
                }

                // net element
                if (element.getString("range").equals("1")) {

                    String[] st = element.getString("list").replace("]", "")
                            .replace("[", "").replaceAll("\"", "").split(",");

                    condition = Condition(st);

                }
                quotaList = getQuotas(level, obj, start, end, condition);

                if (quotaList == null || quotaList.isEmpty() || quotaList.size() == 0) {
                    msg += "quotaList is null.";

                } else {

                    String unit = time.getString("unit");
                    if (unit == null || unit.length() == 0) {
                        msg += "time unit is null.";

                    } else {
                        if (unit.equals("0")) {//min

                            min = 4;
                        } else if (unit.equals("1")) {//hh

                            min = 1;
                        } else if (unit.equals("2")) {//date

                            min = 2;
                        } else if (unit.equals("3")) {//month

                            min = 3;
                        }
                    }

                    if (min != 0 && formulaNameList.size() != 0) {
                        dataList = getValue(start, end, quotaList, formulaNameList, min, quotaList.get(quotaList.size() - 1).getDate("time"), quotaList.get(0).getDate("time"));
                    }
                }
            }
        }

        if (msg.length() == 0 && dataList.size() != 0){
            result.put("result", Constants.SUCCESS);
            result.put("data", dataList);

        } else {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA + msg);
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
                JSONObject Name = new JSONObject();
                for (String formulaName : formulaNameList){

                    d = 0.0;
                    num = 0;
                    JSONObject result1 = new JSONObject();

                    for (JSONObject quolist : quotaList) {

                        Long time = quolist.getDate("time").getTime();

                        if (time >= getMath(start, j, min).getTime()
                                && time < getMath(start, j + 1, min).getTime() && time <= end.getTime()) {

                            if (quolist.getDouble(formulaName) != null
                                    && quolist.getDouble(formulaName) >= 0) {
                                //Time.put("time",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(quolist.getDate("time")));
                                //Name.put("name",quolist.getDate("name"));
                                num++;
                                d = d + quolist.getDouble(formulaName);
                            }
                        }
                        Time.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getMath(start, j, min)));
                    }

                        if (d != 0.0) {
                            result1.put("value", d / num);
                        } else {
                            result1.put("value", 0);
                        }

                        result1.put("quotaName", formulaName);
                        list1.add(result1);
                        result.putAll(Time);
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
        List<JSONObject> quotaList = new ArrayList<JSONObject>();

        switch (level){
            case Constants.LEVEL_GROUP:
                quotaList = obj.getQuotaService().getQuotas(start, end,condition);
                break;

            case Constants.LEVEL_NODE:
                quotaList = obj.getQuotaService().getQuotasNode(start, end,condition);
                break;

            case Constants.LEVEL_CELL:
                quotaList = obj.getQuotaService().getQuotasCell(start, end,condition);
                break;

            default:
                quotaList = null;
                break;
        }
        return quotaList;
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
        JSONObject result = new JSONObject();
        List list = new ArrayList();
        String condition = null;
        Date start = null;
        Date end = null;
        List<JSONObject> quotaList = new ArrayList<>();
        List<String> formulaNameList = new ArrayList<>();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        if (obj == null){
            msg += "Supplier or Generation has error.";

        }else if (quotaHistoryExport == null || quotaHistoryExport.isEmpty()){
            msg += "Parametars is null.";

        }else {
            JSONObject quota = quotaHistoryExport.getJSONObject("quota");
            JSONObject time = quotaHistoryExport.getJSONObject("time");
            JSONObject element = quotaHistoryExport.getJSONObject("element");

            if (quota == null || quota.isEmpty() || !quota.containsKey("range") ||
                    time == null || time.isEmpty() ||
                    !time.containsKey("range") || !time.containsKey("unit") ||
                    element == null || element.isEmpty() || !element.containsKey("range")){
                msg += "Parameters has error.";

            }else {
                // time
                if (time.getString("range").equals("1")) {

                    SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    String startExport = time.getString("start");
                    String endExport = time.getString("end");

                    if (startExport == null || startExport.length() == 0 ||
                            endExport == null || endExport.length() == 0) {
                        msg += "Parameters('time') has error.";

                    } else {
                        start = date.parse(startExport);
                        end = date.parse(endExport);
                    }
                }

                // net element
                if (element.getString("range").equals("1")) {

                    String[] st = element.getString("list").replace("]", "")
                            .replace("[", "").replaceAll("\"", "").split(",");

                    condition = Condition(st);
                }

                // quota
                if (quota.getString("range").equals("1")) {
                    formulaNameList = JSONObject.parseArray(quota.getString("list"), String.class);

                } else if (quota.getString("range").equals("0")) {
                    formulaNameList = obj.getCacheService().getFormulaNameList(false);
                }

                quotaList = getQuotas(level, obj, start, end, condition);

                if (quotaList != null && quotaList.size() != 0) {

                    for (JSONObject export : quotaList) {
                        export.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(export.getDate("time")));
                    }
                }

            }
        }

        if (msg.length() == 0 && quotaList != null && quotaList.size() != 0){
            result.put("result", Constants.SUCCESS);
            result.put("data", quotaList);

        }else{
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_DOWNLOAD_FAILED + msg);
        }

        return result;
    }

    @POST
    @Path("/suppliers/{supplier}/generations/{generation}/nets/{level}/history/counters/download")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject quotaHistoryCounterExport(@RequestParam(value = "quotaHistory") JSONObject quotaHistoryExport,
                                                @PathParam("supplier") String supplier,
                                                @PathParam("generation") String generation,
                                                @PathParam("level") String level,
                                                @HeaderParam("Auth-Token") String authToken) throws ParseException {

        String msg = "";
        String condition = null;
        Date start = null;
        Date end = null;
        JSONObject result = new JSONObject();
        List<JSONObject> counterList  = new ArrayList();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        if (obj == null) {
            msg += "Supplier or Generation has error.";

        } else if (quotaHistoryExport == null || quotaHistoryExport.isEmpty()) {
            msg += "Parametars is null.";

        } else {
//            JSONObject counter = quotaHistoryExport.getJSONObject("counters");
            JSONObject time = quotaHistoryExport.getJSONObject("time");
            JSONObject element = quotaHistoryExport.getJSONObject("element");

            if (time == null || time.isEmpty() ||
                    !time.containsKey("range") || !time.containsKey("unit") ||
//                    counter == null || counter.isEmpty() || !counter.containsKey("range") ||
                    element == null || element.isEmpty() || !element.containsKey("range")) {
                msg += "Parameters has error.";

            } else {
                // time
                if (time.getString("range").equals("1")) {

                    SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    String startExport = time.getString("start");
                    String endExport = time.getString("end");

                    start = date.parse(startExport);
                    end = date.parse(endExport);
                }

                // net element
                if (element.getString("range").equals("1")) {

                    String[] st = element.getString("list").replace("]", "")
                            .replace("[", "").replaceAll("\"", "").split(",");

                    condition = Condition(st);
                }

                // TODO :counter list
                counterList = obj.getQuotaService().getCounterExportGroup(start, end, condition);
            }
        }

        if (msg.length() == 0 && counterList != null && counterList.size() != 0){
            result.put("result", Constants.SUCCESS);
            result.put("data", counterList);

        }else{
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_DOWNLOAD_FAILED + msg);
        }

        return result;
    }

    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/{level}/history/quotas/timelist")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject quotaHistoryCounterTime(@PathParam("supplier") String supplier,
                                              @PathParam("generation") String generation,
                                              @PathParam("level") String level,
                                              @HeaderParam("Auth-Token") String authToken){

        String msg = "";
        JSONObject result = new JSONObject();
        List<JSONObject> dataList = new ArrayList<>();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        if (obj == null){
            msg +="Supplier or Generation is null.";

        }else {

            switch (level) {
                case Constants.LEVEL_GROUP:
                    dataList = obj.getQuotaService().getGroupTime();
                    break;

                case Constants.LEVEL_NODE:
                    dataList = obj.getQuotaService().getNodeTime();
                    break;

                case Constants.LEVEL_CELL:
                    dataList = obj.getQuotaService().getCellTime();
                    break;

                default:
                    dataList = null;
                    break;
            }
        }

        if (msg.length() == 0 && dataList != null && dataList.size() != 0){
            result.put("result", Constants.SUCCESS);
            result.put("data", dataList);

        }else {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA + msg);
        }

        return result;
    }

    @GET
    @Path("/suppliers/{supplier}/generations/{generation}/nets/history/counters/timelist")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject quotaCounterCounterTime(@PathParam("supplier") String supplier,
                                              @PathParam("generation") String generation,
                                              @HeaderParam("Auth-Token") String authToken){

        String msg = "";
        JSONObject result = new JSONObject();
        List<JSONObject> dataList = new ArrayList<>();
        NetObjBase obj = objFactory.getNetObj(supplier, generation);

        if (obj == null){
            msg +="Supplier or Generation is null.";

        }else {
            dataList = obj.getQuotaService().getCounterTime();
        }

        if (msg.length() == 0 && dataList != null && dataList.size() != 0){
            result.put("result", Constants.SUCCESS);
            result.put("data", dataList);

        }else {
            result.put("result", Constants.FAIL);
            result.put("msg", Constants.MSG_NO_DATA + msg);
        }

        return result;
    }
}
