package com.hongshen.sran_service.dao;

import com.hongshen.sran_service.entity.UnicomNodeInfoIndexLte;

public interface UnicomNodeInfoIndexLteMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_node_info_index_lte
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_node_info_index_lte
     *
     * @mbggenerated
     */
    int insert(UnicomNodeInfoIndexLte record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_node_info_index_lte
     *
     * @mbggenerated
     */
    int insertSelective(UnicomNodeInfoIndexLte record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_node_info_index_lte
     *
     * @mbggenerated
     */
    UnicomNodeInfoIndexLte selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_node_info_index_lte
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UnicomNodeInfoIndexLte record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_node_info_index_lte
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UnicomNodeInfoIndexLte record);
}