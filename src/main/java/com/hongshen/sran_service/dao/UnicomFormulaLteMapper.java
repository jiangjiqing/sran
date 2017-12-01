package com.hongshen.sran_service.dao;

import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.entity.UnicomFormula;
import com.hongshen.sran_service.entity.UnicomFormulaLte;
import com.hongshen.sran_service.entity.UnicomFormulaWcdma;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnicomFormulaLteMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_formula_lte
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_formula_lte
     *
     * @mbggenerated
     */
    int insert(UnicomFormulaLte record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_formula_lte
     *
     * @mbggenerated
     */
    int insertSelective(UnicomFormulaLte record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_formula_lte
     *
     * @mbggenerated
     */
    UnicomFormulaLte selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_formula_lte
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UnicomFormulaLte record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_formula_lte
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UnicomFormulaLte record);

    List<JSONObject> getFormulaList();

    @Select("SELECT * FROM unicom_formula_lte")
    List<UnicomFormula> getFormulaLteList();
}