package com.hongshen.sran_service.dao;

import com.hongshen.sran_service.entity.UnicomUserAuhorityLte;

public interface UnicomUserAuhorityLteMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_user_auhority_lte
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String loginName);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_user_auhority_lte
     *
     * @mbggenerated
     */
    int insert(UnicomUserAuhorityLte record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_user_auhority_lte
     *
     * @mbggenerated
     */
    int insertSelective(UnicomUserAuhorityLte record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_user_auhority_lte
     *
     * @mbggenerated
     */
    UnicomUserAuhorityLte selectByPrimaryKey(String loginName);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_user_auhority_lte
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UnicomUserAuhorityLte record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_user_auhority_lte
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UnicomUserAuhorityLte record);
}