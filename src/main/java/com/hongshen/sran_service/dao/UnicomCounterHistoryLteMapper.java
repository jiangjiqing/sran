package com.hongshen.sran_service.dao;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.entity.UnicomCounterHistoryLte;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
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

    List<JSONObject> getCounterHistoryListByTimeOld(@Param("time")String time);

    List<JSONObject> getCellListByNameListAndTime(@Param("nameList")List<String> nameList, @Param("time")String time);

    String getSumCounterByCellsAndCounterAndTime(@Param("nameList")List<String> nameList,
                                                 @Param("counter")String counter,
                                                 @Param("time")String time);

    List<JSONObject> getSumAllCounterByTimeAndCounterList(@Param("time")String time,
                                                          @Param("counterList")List<String> counterList);

    List<JSONObject> getCounterHistoryListByTime(@Param("time")String time);

    List<JSONObject> dowloadCounter(@Param("start")Date start, @Param("end")Date end, @Param("condition") String condition);

    List<JSONObject> getCounterTime();

    void addColumnCounter(@Param("name") String name);
}