package com.hongshen.sran_service.service.util;

import com.alibaba.fastjson.JSONObject;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by poplar on 12/7/17.
 */
public class MapHelper {

    public static JSONObject getGroupLocation(List<JSONObject> list,
                                              String keyLatitude,
                                              String keyLongitude){
        JSONObject result = new JSONObject();
        DecimalFormat df   = new DecimalFormat("######0.0000000000");
        int count = 0;
        Double latitude = 0.0;
        Double longitude = 0.0;

        for (JSONObject node : list) {
            if (node.getDouble(keyLatitude)!=0.0 &&
                    node.getDouble(keyLongitude)!=0.0 &&
                    node.getDouble(keyLatitude)!=null &&
                    node.getDouble(keyLongitude)!=null) {

                count++;
                latitude += node.getDouble(keyLatitude);
                longitude += node.getDouble(keyLongitude);
            }
        }

        result.put("latitude", df.format(latitude/count));
        result.put("longitude", df.format(longitude/count));

        return  result;
    }

}
