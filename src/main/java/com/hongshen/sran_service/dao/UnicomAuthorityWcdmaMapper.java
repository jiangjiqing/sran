package com.hongshen.sran_service.dao;

import com.hongshen.sran_service.entity.UnicomAuthorityWcdma;

public interface UnicomAuthorityWcdmaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_authority_wcdma
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String authorityName);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_authority_wcdma
     *
     * @mbggenerated
     */
    int insert(UnicomAuthorityWcdma record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_authority_wcdma
     *
     * @mbggenerated
     */
    int insertSelective(UnicomAuthorityWcdma record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_authority_wcdma
     *
     * @mbggenerated
     */
    UnicomAuthorityWcdma selectByPrimaryKey(String authorityName);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_authority_wcdma
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UnicomAuthorityWcdma record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_authority_wcdma
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UnicomAuthorityWcdma record);
}