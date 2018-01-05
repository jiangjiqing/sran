package com.hongshen.sran_service.dao;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.entity.UnicomAuthorityWcdma;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
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

    List<JSONObject> getAuthorityList();

    int updateAuthority(@Param("authorityName") String authorityName,@Param("param") JSONObject param);

    int addAuthority(@Param("param") JSONObject param);

    int deleteAuthority(@Param("authorityName")String authorityName);

    JSONObject getAuthByName(@Param("param") JSONObject param);

    List<JSONObject> getAuthorityByLoginNameList(@Param("loginName") String loginName);
}