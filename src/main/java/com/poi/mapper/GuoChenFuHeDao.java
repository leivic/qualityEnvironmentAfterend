package com.poi.mapper;

import com.poi.polo.GuoChenFuHe;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GuoChenFuHeDao {
    void addGuoChenFuHe(GuoChenFuHe guoChenFuHe);

    List<GuoChenFuHe> selectAllGuoChenFuHe();//返回值是一个 GuoChenFuHe类型的集合 这就是泛型
}
