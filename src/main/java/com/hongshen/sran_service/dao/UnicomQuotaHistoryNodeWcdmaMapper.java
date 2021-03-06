package com.hongshen.sran_service.dao;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.entity.UnicomQuotaHistoryNodeWcdma;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UnicomQuotaHistoryNodeWcdmaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_quota_history_node_wcdma
     *
     * @mbggenerated
     */
    int insert(UnicomQuotaHistoryNodeWcdma record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_quota_history_node_wcdma
     *
     * @mbggenerated
     */
    int insertSelective(UnicomQuotaHistoryNodeWcdma record);


    Date getQuotaLastUpdateTime();
    
    JSONObject getQuota(@Param("nodeName")String nodeName,
                        @Param("time")String time);

    JSONObject getLevel(@Param("nodeName") String nodeName,
                        @Param("time")String time);

    void addQuotaHistoryNodeList(@Param("cloumns")List<String> cloumns,
                                 @Param("valueList")List<String> valueList);

    List<JSONObject> getQuotasNode(@Param("start")Date start,
                                   @Param("end")Date end,
                                   @Param("condition")String condition);

    List<JSONObject> getNodeTime();

    void addColumn(@Param("formula") String formula);

    void deleteColumn(@Param("quotaName") String quotaName);

    void setColumn(@Param("oldquotaName")String oldquotaName,@Param("quotaName") String quotaName);
}