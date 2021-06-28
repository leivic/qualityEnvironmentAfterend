package com.poi.service;

import com.github.pagehelper.PageHelper;
import com.poi.mapper.UserDao;
import com.poi.polo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public User findByName(String username){
        return userDao.findByName(username);
    };

    public List<Map<String,Object>> findAllUser(int pageNum)throws Exception{
        PageHelper.startPage(pageNum,8);//pageHelper插件的核心代码 一个static方法 starPage
        return userDao.findAllUser();
    }
}
