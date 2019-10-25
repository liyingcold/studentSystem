package com.ly.dao;

import com.ly.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    //   id查看信息
    User selectByPrimaryKey(Long id);
    //    用户登录
    User selectLogin(@Param("username")String userName, @Param("password")String password);
//    插入
    int insert(User user);
//    获取所有用户信息
    List<User> selectAll();
}
