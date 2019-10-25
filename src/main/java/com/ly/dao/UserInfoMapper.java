package com.ly.dao;

import com.ly.pojo.UserInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoMapper {

    UserInfo selectByPrimaryKey(Long id);


}