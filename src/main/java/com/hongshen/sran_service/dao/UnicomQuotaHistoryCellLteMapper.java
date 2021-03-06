package com.hongshen.sran_service.dao;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.entity.UnicomQuotaHistoryCellLte;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface UnicomQuotaHistoryCellLteMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_quota_history_cell_lte
     *
     * @mbggenerated
     */
    int insert(UnicomQuotaHistoryCellLte record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_quota_history_cell_lte
     *
     * @mbggenerated
     */
    int insertSelective(UnicomQuotaHistoryCellLte record);


    Date getQuotaLastUpdateTime();

    JSONObject getQuota(@Param("cellName")String cellName,
                        @Param("time")String time);

    JSONObject getLevel(@Param("cellName") String cellName,
                        @Param("time")String time);

    void addQuotaHistoryCellList(@Param("cloumns")List<String> cloumns,
                                 @Param("valueList")List<String> valueList);

    List<JSONObject> getQuotasCell(@Param("start") Date start,
                                   @Param("end")Date end,
                                   @Param("condition") String condition);

    List<JSONObject> getCellTime();

    void addColumn(@Param("formula")String formula);

    void deleteColumn(@Param("quotaName") String quotaName);

    void setColumn(@Param("oldquotaName")String oldquotaName,@Param("quotaName") String quotaName);

    List<JSONObject> getCellFormula(@Param("start") Date start,
                                    @Param("end") Date end,
                                    @Param("cell") String cell);
}