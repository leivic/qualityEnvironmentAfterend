package com.poi.mapper;

import com.poi.polo.GuoChenFuHe;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GuoChenFuHeDao {
    void addGuoChenFuHe(GuoChenFuHe guoChenFuHe);

    List<GuoChenFuHe> selectAllGuoChenFuHe();//返回值是一个 GuoChenFuHe类型的集合 这就是泛型

    List<GuoChenFuHe> selectGuoChenFuHeListByDate(@Param("date")String date, @Param("pingShengXingZhi")String pingShengXingZhi);//mybtis传入多个参数时需要 @Param
    //上面ALL是全部数据 返回在列表里面 这里是按月份和性质筛查数据 返回在Echarts里面
    void deletGuoChenByid(int id);
}
