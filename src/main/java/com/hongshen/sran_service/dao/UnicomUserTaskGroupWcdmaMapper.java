package com.hongshen.sran_service.dao;

import com.hongshen.sran_service.entity.UnicomUserTaskGroupWcdma;
import com.hongshen.sran_service.entity.UnicomUserTaskGroupWcdmaWithBLOBs;

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
}