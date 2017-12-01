package com.hongshen.sran_service.dao;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.entity.UnicomAlarmWcdma;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UnicomAlarmWcdmaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_alarm_wcdma
     *
     * @mbggenerated
     */
    int insert(UnicomAlarmWcdma record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_alarm_wcdma
     *
     * @mbggenerated
     */
    int insertSelective(UnicomAlarmWcdma record);

    List<JSONObject> getAllAlarmInfo();

    List<JSONObject> getGroupAlarmByName(@Param("groupName")String groupName);

    List<JSONObject> getNodeAlarmByName(@Param("nodeName")String nodeName);

    List<JSONObject> getCellAlarmByName(@Param("cellName")String cellName);

}