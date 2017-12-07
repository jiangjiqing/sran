package com.hongshen.sran_service.service.util;
import com.alibaba.fastjson.JSONObject;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by poplar on 12/7/17.
 */
public class MapHelper {
    public  JSONObject getGroupLocation(List<JSONObject> list,String keyLatitude,String keyLongitude){
        JSONObject result = new JSONObject();
        DecimalFormat df   = new DecimalFormat("######0.0000000000");
        int num=0;
        Double latitude = 0.0;
        Double longitude = 0.0;
        for (int i=0;i<list.size();i++) {
            if (list.get(i).getDouble(keyLatitude)!=0.0&& list.get(i).getDouble(keyLongitude)!=0.0
                    &&list.get(i).getDouble(keyLatitude)!=null&& list.get(i).getDouble(keyLongitude)!=null) {
                num++;
                latitude  =latitude + list.get(i).getDouble(keyLatitude);
                longitude =longitude + list.get(i).getDouble(keyLongitude);
            }
        }
        result.put("latitude",df.format(latitude/num));
        result.put("longitude",df.format(longitude/num));
        //Double[] result= {Double.parseDouble(df.format(latitude/num)),Double.parseDouble(df.format(longitude/num))};
        return  result;
    }

}
