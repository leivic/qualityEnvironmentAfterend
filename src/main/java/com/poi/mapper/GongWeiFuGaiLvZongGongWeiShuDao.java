package com.poi.mapper;

import com.poi.polo.GongWeiFuGaiLVZongGongWeiShu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GongWeiFuGaiLvZongGongWeiShuDao {
    List<GongWeiFuGaiLVZongGongWeiShu> selectAll();

    void change(@Param("quYu")String quYu,@Param("shuliang")String shuliang);

    List<GongWeiFuGaiLVZongGongWeiShu> selectByQuYu(String quYu);
}
