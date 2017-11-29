package com.hongshen.sran_service.dao;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.entity.UnicomFormula;
import com.hongshen.sran_service.entity.UnicomFormulaWcdma;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnicomFormulaWcdmaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_formula_wcdma
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_formula_wcdma
     *
     * @mbggenerated
     */
    int insert(UnicomFormulaWcdma record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_formula_wcdma
     *
     * @mbggenerated
     */
    int insertSelective(UnicomFormulaWcdma record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_formula_wcdma
     *
     * @mbggenerated
     */
    UnicomFormulaWcdma selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_formula_wcdma
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UnicomFormulaWcdma record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_formula_wcdma
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UnicomFormulaWcdma record);

    List<JSONObject> getFormulaList();

    @Select("SELECT * FROM unicom_formula_wcdma")
    List<UnicomFormula> getFormulaWcdmaList();
}