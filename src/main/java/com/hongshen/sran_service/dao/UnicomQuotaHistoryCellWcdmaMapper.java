package com.hongshen.sran_service.dao;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.entity.UnicomQuotaHistoryCellWcdma;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UnicomQuotaHistoryCellWcdmaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_quota_history_cell_wcdma
     *
     * @mbggenerated
     */
    int insert(UnicomQuotaHistoryCellWcdma record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_quota_history_cell_wcdma
     *
     * @mbggenerated
     */
    int insertSelective(UnicomQuotaHistoryCellWcdma record);

    JSONObject getQuotaByName(@Param("cellName")String cellName);

    int getLevelByName(@Param("cellName") String cellName);

    JSONObject getCellLevel(@Param("cellName")String cellName);
}