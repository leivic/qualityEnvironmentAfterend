package com.poi.mapper;


import com.poi.polo.BianHuaDian;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BianHuaDianDao {
    void addBianHuaDian(BianHuaDian bianHuaDian);
    void deleteBianHuaDianById(int id);
    List<BianHuaDian> selectALLBianHuaDian();
    List<BianHuaDian> selectBianHuaDianByDate(String date);
}
