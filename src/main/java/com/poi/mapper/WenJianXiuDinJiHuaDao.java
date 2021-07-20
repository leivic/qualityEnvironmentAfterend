package com.poi.mapper;

import com.poi.polo.WenJianXiuDinJiHua;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hibernate.validator.constraints.EAN;

import java.util.List;

@Mapper
public interface WenJianXiuDinJiHuaDao {
    List<WenJianXiuDinJiHua> selectWenJianXiuDinJiHuaByDate(String date);//根据月份获得文件修订计划数据

    void changeWenJianXiuDinJiHua(@Param("id")int id, @Param("jihuashu")String jihuashu);//传参入id和计划数 根据id改变计划数

    void addWenJianXiuDinjihua(@Param("quYu")String quYu,@Param("jiHuaShu")String jiHuaShu,@Param("date")String date);//三项数据传参 手工添加数据

    void deletejiHua(String id);
}
