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

    List<JSONObject> getGroupList();

    List<JSONObject> getGroupInfoList();

    List<String> getGroupNameList();

    JSONObject getGroupInfo(@Param("groupName") String groupName);

    List<JSONObject> getRoomList();

    List<String> getRoomNameList();

    List<String> getOssNameList();

    List<JSONObject> getGroupListByRoom(@Param("roomName")String roomName);

    List<String> getGroupNameListByRoom(@Param("roomName")String roomName);

    Integer addRnc(JSONObject jsonObject);

    Integer deleteGroup();

    Integer getGroupCounter();
}