package com.hongshen.sran_service.dao;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.entity.UnicomGroupWcdma;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
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

    JSONObject getGroupByName(String groupName);

    List<JSONObject> getGroupList();

    List<JSONObject> getSpecifiedGroupList(@Param("groupName") String groupName);
}