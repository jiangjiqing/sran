package com.hongshen.sran_service.dao;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.entity.UnicomCounterHistoryLte;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UnicomCounterHistoryLteMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_counter_history_lte
     *
     * @mbggenerated
     */
    int insert(UnicomCounterHistoryLte record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_counter_history_lte
     *
     * @mbggenerated
     */
    int insertSelective(UnicomCounterHistoryLte record);

    List<JSONObject> getCounterHistoryLteListByTime(@Param("time")String time);
}