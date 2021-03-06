package com.hongshen.sran_service.dao;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.entity.UnicomUserAuthorityWcdma;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UnicomUserAuthorityWcdmaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_user_authority_wcdma
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String loginName);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_user_authority_wcdma
     *
     * @mbggenerated
     */
    int insert(UnicomUserAuthorityWcdma record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_user_authority_wcdma
     *
     * @mbggenerated
     */
    int insertSelective(UnicomUserAuthorityWcdma record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_user_authority_wcdma
     *
     * @mbggenerated
     */
    UnicomUserAuthorityWcdma selectByPrimaryKey(String loginName);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_user_authority_wcdma
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UnicomUserAuthorityWcdma record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_user_authority_wcdma
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UnicomUserAuthorityWcdma record);

    List<String> getWcdmaAuth(@Param("name")String name);

    int addWcdmaUserAuthory(@Param("authorityName")String authorityName,@Param("loginName")String loginName);


    int updateWcdmaUserAuthory(@Param("authorityName") String authorityName,@Param("loginName") String loginName);

    int deletewcdmaUserAuthory(@Param("loginName") String loginName);
}