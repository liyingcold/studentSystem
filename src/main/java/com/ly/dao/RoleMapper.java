package com.ly.dao;

import com.ly.pojo.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMapper {

    Role selectByPrimaryKey(Long id);



}