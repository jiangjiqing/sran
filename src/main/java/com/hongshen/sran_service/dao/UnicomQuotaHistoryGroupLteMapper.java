package com.hongshen.sran_service.dao;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.entity.UnicomQuotaHistoryGroupLte;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UnicomQuotaHistoryGroupLteMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_quota_history_group_lte
     *
     * @mbggenerated
     */
    int insert(UnicomQuotaHistoryGroupLte record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_quota_history_group_lte
     *
     * @mbggenerated
     */
    int insertSelective(UnicomQuotaHistoryGroupLte record);

    JSONObject getQuota(@Param("groupName")String groupName,
                        @Param("time")String time);

    JSONObject getLevel(@Param("groupName") String groupName,
                        @Param("time")String time);

    List<JSONObject>  getQuotas(@Param("start")Date start,
                                @Param("end")Date end,
                                @Param("condition")String condition);

    void addQuotaHistoryGroupList(@Param("cloumns")List<String> cloumns,
                                  @Param("valueList")List<String> valueList);
}