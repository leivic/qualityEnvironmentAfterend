package com.poi.mapper;

import com.poi.polo.WenTiQinDan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WenTiQinDanDao {

    List<WenTiQinDan> selectALLWenTi();

    List<WenTiQinDan> selectWenTiByDate(String date);

    void changeZhuangTai(@Param("zhuangTai")String zhuangTai, @Param("id")String id);

    void addWenTi(WenTiQinDan wenTiQinDan);

}
