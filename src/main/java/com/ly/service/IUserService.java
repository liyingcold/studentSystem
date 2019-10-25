package com.ly.service;

import com.ly.common.ServerResponse;
import com.ly.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IUserService {
   ServerResponse<User> login(@Param("username") String username, @Param("password") String password);
   ServerResponse<String> insert(@Param("user")User user);
   ServerResponse<List<User>> selectAll();
//   角色查询
}
