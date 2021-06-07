package com.poi.service;

import com.poi.mapper.UserDao;
import com.poi.polo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public User findByName(String username){
        return userDao.findByName(username);
    }
}
