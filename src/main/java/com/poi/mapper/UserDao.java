package com.poi.mapper;

import com.poi.polo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component(value = "UserDao")
public interface UserDao {
    User findByName(String username);
}
