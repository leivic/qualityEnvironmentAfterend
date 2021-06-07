package com.poi.mapper;

import com.poi.polo.Permission;
import com.poi.polo.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "PermissionDao")
public interface PermissionDao {
    List<Permission> selectListByUserId(Integer id);
    List<Role> selectRoleListByUserId(Integer id);
}
