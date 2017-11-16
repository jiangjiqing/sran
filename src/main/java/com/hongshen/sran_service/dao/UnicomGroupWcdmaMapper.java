package com.hongshen.sran_service.dao;

import com.hongshen.sran_service.entity.UnicomGroupWcdma;

public interface UnicomGroupWcdmaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_group_wcdma
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String groupName);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_group_wcdma
     *
     * @mbggenerated
     */
    int insert(UnicomGroupWcdma record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_group_wcdma
     *
     * @mbggenerated
     */
    int insertSelective(UnicomGroupWcdma record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_group_wcdma
     *
     * @mbggenerated
     */
    UnicomGroupWcdma selectByPrimaryKey(String groupName);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_group_wcdma
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UnicomGroupWcdma record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_group_wcdma
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(UnicomGroupWcdma record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_group_wcdma
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UnicomGroupWcdma record);
}