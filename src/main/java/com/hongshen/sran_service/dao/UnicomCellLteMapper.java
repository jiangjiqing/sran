package com.hongshen.sran_service.dao;
import com.alibaba.fastjson.JSONObject;
import com.hongshen.sran_service.entity.UnicomCellLte;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UnicomCellLteMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_cell_lte
     *
     * @mbggenerated
     */
    int insert(UnicomCellLte record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unicom_cell_lte
     *
     * @mbggenerated
     */
    int insertSelective(UnicomCellLte record);

    JSONObject getCellInfo(@Param("cellName") String cellName);

    List<JSONObject> getCellListByNode(@Param("nodeName") String nodeName);

    List<String> getCellNameListByNode(@Param("nodeName")String nodeName);

    List<JSONObject> getCellInfoList();//todo

    List<String> getCellNameList();
}