package com.poi.mapper;

import com.poi.polo.GongWeiFuGaiLv;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GongWeiFuGaiLvDao {

    void addFuGaiLV(GongWeiFuGaiLv gongWeiFuGaiLv);//按一定条件避免重复的插入工位数据 以每个GongWeiFuGaiLv对象为参数

    List<GongWeiFuGaiLv> selectGongWeiFuGaiLvByYear(String year);//按年份选择工位数据

    List<GongWeiFuGaiLv> selectAllGongWeiFuGaiLv();//选择所有工位数据

    List<GongWeiFuGaiLv> SelectGongWeiFUGaiLvByQuyu(String quYu,String year);//按区域和年份选择工位

    int selectGongWeiShuByYueFen(@Param("year")String year, @Param("yueFen")String yueFen);//select count 按年份和月份查找 查出特定月份的个数
}
