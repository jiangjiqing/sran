package com.hongshen.sran_service.dao;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.entity.UnicomNodeWcdma;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UnicomNodeWcdmaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_node_wcdma
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String nodeName);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_node_wcdma
     *
     * @mbggenerated
     */
    int insert(UnicomNodeWcdma record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_node_wcdma
     *
     * @mbggenerated
     */
    int insertSelective(UnicomNodeWcdma record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_node_wcdma
     *
     * @mbggenerated
     */
    UnicomNodeWcdma selectByPrimaryKey(String nodeName);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_node_wcdma
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UnicomNodeWcdma record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_node_wcdma
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UnicomNodeWcdma record);

    List<JSONObject> getNodeByName(@Param("groupName") String groupName);


    List<JSONObject> getNodeList(@Param("groupName") String groupName);

    List<JSONObject> getSpecifiedNodeList(@Param("groupName")String groupName, @Param("nodeName") String nodeName);
}