package com.hongshen.sran_service.dao;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.entity.UnicomQuotaGroupLte;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnicomQuotaGroupLteMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_quota_group_lte
     *
     * @mbggenerated
     */
    int insert(UnicomQuotaGroupLte record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_quota_group_lte
     *
     * @mbggenerated
     */
    int insertSelective(UnicomQuotaGroupLte record);

    JSONObject getGroupByName(String groupName);

    List<JSONObject> getGroupList();
}