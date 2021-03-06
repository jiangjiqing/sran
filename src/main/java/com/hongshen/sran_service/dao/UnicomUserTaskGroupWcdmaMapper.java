package com.hongshen.sran_service.dao;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.entity.UnicomUserTaskGroupWcdma;
import com.hongshen.sran_service.entity.UnicomUserTaskGroupWcdmaWithBLOBs;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnicomUserTaskGroupWcdmaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_user_task_group_wcdma
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String loginName);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_user_task_group_wcdma
     *
     * @mbggenerated
     */
    int insert(UnicomUserTaskGroupWcdmaWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_user_task_group_wcdma
     *
     * @mbggenerated
     */
    int insertSelective(UnicomUserTaskGroupWcdmaWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_user_task_group_wcdma
     *
     * @mbggenerated
     */
    UnicomUserTaskGroupWcdmaWithBLOBs selectByPrimaryKey(String loginName);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_user_task_group_wcdma
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UnicomUserTaskGroupWcdmaWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_user_task_group_wcdma
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(UnicomUserTaskGroupWcdmaWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_user_task_group_wcdma
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UnicomUserTaskGroupWcdma record);

    List<JSONObject> getTaskList(@Param("loginName")String loginName);

    int addTask(@Param("loginName")String loginName ,@Param("param")JSONObject param );

    JSONObject  getTaskInfo(@Param("loginName") String loginName);

    List<JSONObject>  getTaskTime();

}