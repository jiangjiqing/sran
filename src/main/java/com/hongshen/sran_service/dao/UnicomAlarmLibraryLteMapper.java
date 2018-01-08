package com.hongshen.sran_service.dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.entity.UnicomAlarmLibraryLte;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnicomAlarmLibraryLteMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_alarm_library_lte
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String alarmName);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_alarm_library_lte
     *
     * @mbggenerated
     */
    int insert(UnicomAlarmLibraryLte record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_alarm_library_lte
     *
     * @mbggenerated
     */
    int insertSelective(UnicomAlarmLibraryLte record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_alarm_library_lte
     *
     * @mbggenerated
     */
    UnicomAlarmLibraryLte selectByPrimaryKey(String alarmName);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_alarm_library_lte
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UnicomAlarmLibraryLte record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_alarm_library_lte
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UnicomAlarmLibraryLte record);

    List<JSONObject> getAlarmList();

    JSONObject getAlarmByName(String alarmName);

    int updateAlarmByName(@Param("alarmName")String alarmName,
                          @Param("param")JSONObject param);

    int addAlarm(@Param("param")JSONObject param);

    int deleteAlarmByName(@Param("alarmName")String alarmName);

    List<JSONObject> getAlarmInfoList();

    int addAlarms(@Param("importJson") JSONArray importJson);
}