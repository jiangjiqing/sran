package com.hongshen.sran_service.dao;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.entity.UnicomAlarmLibraryWcdma;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnicomAlarmLibraryWcdmaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_alarm_library_wcdma
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String alarmName);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_alarm_library_wcdma
     *
     * @mbggenerated
     */
    int insert(UnicomAlarmLibraryWcdma record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_alarm_library_wcdma
     *
     * @mbggenerated
     */
    int insertSelective(UnicomAlarmLibraryWcdma record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_alarm_library_wcdma
     *
     * @mbggenerated
     */
    UnicomAlarmLibraryWcdma selectByPrimaryKey(String alarmName);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_alarm_library_wcdma
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UnicomAlarmLibraryWcdma record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_alarm_library_wcdma
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UnicomAlarmLibraryWcdma record);

    List<JSONObject> getAlarmList();

    JSONObject getAlarmByName(@Param("alarmName")String alarmName);

    int updateAlarmByName(@Param("alarmName")String alarmName,
                          @Param("param")JSONObject param);

    int addAlarm(@Param("param")JSONObject param);
}