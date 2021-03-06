package com.hongshen.sran_service.dao;

import com.hongshen.sran_service.entity.UnicomUserTaskWcdma;
import com.hongshen.sran_service.entity.UnicomUserTaskWcdmaKey;

public interface UnicomUserTaskWcdmaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_user_task_wcdma
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(UnicomUserTaskWcdmaKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_user_task_wcdma
     *
     * @mbggenerated
     */
    int insert(UnicomUserTaskWcdma record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_user_task_wcdma
     *
     * @mbggenerated
     */
    int insertSelective(UnicomUserTaskWcdma record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_user_task_wcdma
     *
     * @mbggenerated
     */
    UnicomUserTaskWcdma selectByPrimaryKey(UnicomUserTaskWcdmaKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_user_task_wcdma
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UnicomUserTaskWcdma record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_user_task_wcdma
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(UnicomUserTaskWcdma record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_user_task_wcdma
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UnicomUserTaskWcdma record);
}