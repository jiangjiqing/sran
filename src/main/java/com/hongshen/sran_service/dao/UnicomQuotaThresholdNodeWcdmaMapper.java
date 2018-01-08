package com.hongshen.sran_service.dao;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.entity.UnicomQuotaThresholdNodeWcdma;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnicomQuotaThresholdNodeWcdmaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_quota_threshold_node_wcdma
     *
     * @mbggenerated
     */
    int insert(UnicomQuotaThresholdNodeWcdma record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_quota_threshold_node_wcdma
     *
     * @mbggenerated
     */
    int insertSelective(UnicomQuotaThresholdNodeWcdma record);

    List<JSONObject> getThresholdList();

    Integer setThreshold(JSONObject params);
}