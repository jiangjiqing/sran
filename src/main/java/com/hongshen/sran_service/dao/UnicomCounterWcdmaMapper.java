package com.hongshen.sran_service.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.entity.UnicomCounterWcdma;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnicomCounterWcdmaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_counter_wcdma
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_counter_wcdma
     *
     * @mbggenerated
     */
    int insert(UnicomCounterWcdma record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_counter_wcdma
     *
     * @mbggenerated
     */
    int insertSelective(UnicomCounterWcdma record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_counter_wcdma
     *
     * @mbggenerated
     */
    UnicomCounterWcdma selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_counter_wcdma
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UnicomCounterWcdma record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_counter_wcdma
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UnicomCounterWcdma record);

    List<JSONObject> getCounterList();
}