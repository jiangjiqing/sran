package com.hongshen.sran_service.dao;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.entity.UnicomFavoriteWcdma;
import com.hongshen.sran_service.entity.UnicomNodeWcdma;
import org.springframework.stereotype.Repository;

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

    List<JSONObject> getFavoriteList();
}