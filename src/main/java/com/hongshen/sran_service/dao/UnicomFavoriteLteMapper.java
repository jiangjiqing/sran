package com.hongshen.sran_service.dao;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.entity.UnicomFavoriteLte;
import com.hongshen.sran_service.entity.UnicomNodeWcdma;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Reader;
import java.util.List;
@Repository
public interface UnicomFavoriteLteMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_favorite_lte
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String loginName);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_favorite_lte
     *
     * @mbggenerated
     */
    int insert(UnicomFavoriteLte record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_favorite_lte
     *
     * @mbggenerated
     */
    int insertSelective(UnicomFavoriteLte record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_favorite_lte
     *
     * @mbggenerated
     */
    UnicomFavoriteLte selectByPrimaryKey(String loginName);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_favorite_lte
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UnicomFavoriteLte record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_favorite_lte
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UnicomFavoriteLte record);

    List<JSONObject> getFavoriteList(@Param("TableName") String TableName);

    int cancelCollection(@Param("name")String name);

    int addCollection(@Param("name")String name);

    JSONObject getTable(@Param("gettableName")String gettableName);

    int deleteNode(@Param("tableName")String tableName,@Param("name") String name);

    int deleteNodes(@Param("tableName") String tableName,@Param("name") String name);

    int addNode(@Param("tableName")String tableName,@Param("name") String name);

    int addNodes(@Param("tableName")String tableName,@Param("nodeNames") List<JSONObject> nodeNames);

    JSONObject getGroupTable(@Param("tableNameLike")String tableNameLike);

    Integer getNodeNum(@Param("tableName") String tableName, @Param("groupName")String groupName);

    Integer getNodeofNull(@Param("tableName")String tableName,@Param("nodeName") String nodeName);

    JSONObject geNodeTable(@Param("tableNameLike")String tableNameLike);
}