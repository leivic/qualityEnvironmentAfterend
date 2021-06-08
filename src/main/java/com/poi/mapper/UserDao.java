package com.poi.mapper;

import com.poi.polo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component(value = "UserDao")
public interface UserDao {
    User findByName(String username);

    List<Map<String,Object>> findAllUser();//多表查询 返回类型不能是实体类 应该是一个Map的键值对
}
