package com.poi.mapper;

import com.poi.polo.Model1;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface Model1Dao {

    void addModel1(Model1 model1);//将一条model1数据加入数据库

    List<Model1> selectModel1ByDate(String Date);//根据日期 查出一个以Model1对象为元素的集合

    Void deleteModel1ByDate(String Date);//根据日期删除数据库里的数据


}
