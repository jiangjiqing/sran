package com.hongshen.sran_service.dao;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.entity.UnicomFavoriteWcdma;
import com.hongshen.sran_service.entity.UnicomNodeWcdma;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Reader;
import java.util.List;
@Repository
public interface UnicomFavoriteWcdmaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_favorite_wcdma
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String loginName);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_favorite_wcdma
     *
     * @mbggenerated
     */
    int insert(UnicomFavoriteWcdma record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_favorite_wcdma
     *
     * @mbggenerated
     */
    int insertSelective(UnicomFavoriteWcdma record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_favorite_wcdma
     *
     * @mbggenerated
     */
    UnicomFavoriteWcdma selectByPrimaryKey(String loginName);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_favorite_wcdma
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UnicomFavoriteWcdma record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_favorite_wcdma
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UnicomFavoriteWcdma record);

    List<JSONObject> getFavoriteList(@Param("TableName") String TableName);

    int cancelCollection(@Param("name") String name);

    int addCollection(@Param("name") String name);

    JSONObject getTable(@Param("tableNameLike")String tableNameLike);

    int deleteNode(@Param("tableName")String tableName,@Param("name") String name);

    int deleteNodes(@Param("tableName") String tableName,@Param("name") String name);

    int addNode(@Param("tableName")String tableName,@Param("name") String name);

    int addNodes(@Param("tableName")String tableName,@Param("nodeNames") List<JSONObject> nodeNames);

    Integer getNodeNum(@Param("tableName") String tableName, @Param("groupName")String groupName);

    Integer getNodeofNull(@Param("tableName")String tableName,@Param("nodeName") String nodeName);
}