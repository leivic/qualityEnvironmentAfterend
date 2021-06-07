package com.poi.service;

import com.poi.mapper.PermissionDao;
import com.poi.polo.Permission;
import com.poi.polo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {
    @Autowired
    private PermissionDao permissionDao;

    public List<Permission> selectListByUserId(Integer id){
        return permissionDao.selectListByUserId(id);
    }

    public List<Role> selectRoleListByUserId(Integer id){
        return permissionDao.selectRoleListByUserId(id);
    }
}
