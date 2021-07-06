package com.poi.mapper;

import com.poi.polo.GongWeiFuGaiLVZongGongWeiShu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GongWeiFuGaiLvZongGongWeiShuDao {
    List<GongWeiFuGaiLVZongGongWeiShu> selectAll();

    void change(String quYu);
}
