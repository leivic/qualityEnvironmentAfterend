package com.poi.mapper;

import com.poi.polo.GongWeiFuHe;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GongWeiFuHeDao {
    void addGongWeiFuHe(GongWeiFuHe gongWeiFuHe);//添加数据

    List<GongWeiFuHe> selectGongWeiFuHeListByDate(String date);//将年月拆开  两个参数查找对应日期信息

    void deleteGongWeiFuHeById(int id);


}
