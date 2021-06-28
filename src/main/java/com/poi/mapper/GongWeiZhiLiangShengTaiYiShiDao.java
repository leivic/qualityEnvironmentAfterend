package com.poi.mapper;

import com.poi.polo.GongWeiZhiLiangShengTaiYiShi;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GongWeiZhiLiangShengTaiYiShiDao {
    List<GongWeiZhiLiangShengTaiYiShi> selectALLShengTaiYiShiData();//查询所有数据

    void addShengTaiYiShiData(GongWeiZhiLiangShengTaiYiShi gongWeiZhiLiangShengTaiYiShi);

    List<GongWeiZhiLiangShengTaiYiShi> selectShengTaiYiShiDataByDate(String date);

    void deleteGongWeiZhiLiangShengTaiYiShiById(int id);
}
