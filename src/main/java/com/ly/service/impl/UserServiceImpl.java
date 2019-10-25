package com.ly.service.impl;

import com.ly.common.ServerResponse;
import com.ly.dao.UserMapper;
import com.ly.pojo.User;
import com.ly.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "iUserService")
@Slf4j
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        User user=userMapper.selectLogin(username,password);
        if (user==null){
            return ServerResponse.createByErrorMessage("找不到当前用户！");
        }

        return ServerResponse.createBySuccess(user);
    }

    @Override
    public ServerResponse<String> insert(User user) {
        log.info("user------>",user.getUserName());
        if (user.getUserName().equals("")){
            return ServerResponse.createByErrorMessage("信息不可为空");
        }
        int resultCount=userMapper.insert(user);
        if (resultCount==0){
            return ServerResponse.createByErrorMessage("注册失败！");
        }
        return ServerResponse.createBySuccess("注册成功");
    }

    @Override
    public ServerResponse<List<User>> selectAll() {
        List<User> userList=userMapper.selectAll();
        return ServerResponse.createBySuccess(userList);
    }

}
