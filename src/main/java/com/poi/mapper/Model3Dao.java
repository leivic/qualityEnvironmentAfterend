package com.poi.mapper;

import com.poi.polo.Model3;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper //@Mapper注解来自mybatis包，可以不加该注解 那么就要在启动类加 @Mapperscan 扫描mapper下的所有 都加入spring生成bean
public interface Model3Dao {
    public void addModel3(Model3 model3);//将对象数据添加进数据库

    List<Model3> selectModel3ByMonth(String month);//根据月份查出数据库数据 返回Model3类型的List
}
