package com.hongshen.sran_service.dao;

import com.hongshen.sran_service.entity.UnicomNodeInfoIndexWcdma;

public interface UnicomNodeInfoIndexWcdmaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_node_info_index_wcdma
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_node_info_index_wcdma
     *
     * @mbggenerated
     */
    int insert(UnicomNodeInfoIndexWcdma record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_node_info_index_wcdma
     *
     * @mbggenerated
     */
    int insertSelective(UnicomNodeInfoIndexWcdma record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_node_info_index_wcdma
     *
     * @mbggenerated
     */
    UnicomNodeInfoIndexWcdma selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_node_info_index_wcdma
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UnicomNodeInfoIndexWcdma record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_node_info_index_wcdma
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UnicomNodeInfoIndexWcdma record);
}