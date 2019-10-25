package com.ly.controller;

import com.ly.common.Const;
import com.ly.common.ServerResponse;
import com.ly.pojo.Role;
import com.ly.pojo.User;
import com.ly.service.IUserService;
import com.ly.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "/manage/user")
@Slf4j
public class BackendController {

    @Autowired
    private IUserService iUserService;

//    @RequestMapping(value = "login",method = RequestMethod.POST)
//    @ResponseBody
//    public ServerResponse<User> login(String username,String password,HttpSession session){
//        ServerResponse<User> response=iUserService.login(username, password);
//
//        if (response.isSuccess()){
//            User user=response.getData();
//            log.info("user-role--------->",user.getRoleList());
//            for (Role role:(user.getRoleList())){
//                if (role.getId()== Const.Role.ROLE_ADMIN){
//                    session.setAttribute(Const.CURRENT_USER,user);
//                    return response;
//                }else {
//                    return ServerResponse.createByErrorMessage("不是管理员，无法登录");
//                }
//            }
//        }
//        return response;
//    }



}
