package com.hongshen.sran_service.dao;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.entity.UnicomQuotaThresholdCellWcdma;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnicomQuotaThresholdCellWcdmaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_quota_threshold_cell_wcdma
     *
     * @mbggenerated
     */
    int insert(UnicomQuotaThresholdCellWcdma record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_quota_threshold_cell_wcdma
     *
     * @mbggenerated
     */
    int insertSelective(UnicomQuotaThresholdCellWcdma record);

    List<JSONObject> getThresholdCellList();

    Integer setCell(JSONObject quotaThres);
}