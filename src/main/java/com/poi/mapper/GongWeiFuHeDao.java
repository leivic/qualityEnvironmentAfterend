package com.poi.mapper;

import com.poi.polo.GongWeiFuHe;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GongWeiFuHeDao {
    void addGongWeiFuHe(GongWeiFuHe gongWeiFuHe);//添加数据

    List<GongWeiFuHe> selectGongWeiFuHeListByDate(@Param("date")String date, @Param("pingShengXingZhi")String pingShengXingZhi);//mybtis传入多个参数时需要 @Param

    void deleteGongWeiFuHeById(int id);

    List<GongWeiFuHe> selectAllGongWeiFuHe();//查询所有值
}
