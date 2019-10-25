package com.ly.controller;

import com.ly.common.Const;
import com.ly.common.ServerResponse;
import com.ly.pojo.User;
import com.ly.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
@Slf4j
public class UserController {

    Map<String ,String> map=new HashMap<>();
    @Autowired
    private IUserService iUserService;

//    登录
    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(@RequestParam("username") String username, @RequestParam("password") String password,HttpSession session){
        ServerResponse<User> response=iUserService.login(username, password);
        if (response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }

        return response;
    }

//退出登录
    @RequestMapping(value = "logout",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);

        return ServerResponse.createBySuccess();
    }


//    注册
    @RequestMapping(value = "insert",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> insert(@RequestBody User user){
        log.info("user----->",user.getUserName());
        return iUserService.insert(user);
    }

//    获取当前的登录用户的信息
    @RequestMapping(value = "get_user_info",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session){
//        判断登录状态
        User user= (User) session.getAttribute(Const.CURRENT_USER);
        if (user!=null){
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("用户未登录，无法获取信息");
    }


//    @RequestMapping( value = "test",method = RequestMethod.GET)
//    @ResponseBody
//    public Map<String, String> test(){
//        map.put("1","2");
//        return map;
//    }

//    查看所有
@RequestMapping(value = "/getAllInformation",method = RequestMethod.POST)
@ResponseBody
public ServerResponse<List<User>> getAllInformation(HttpSession session){
    User user= (User) session.getAttribute(Const.CURRENT_USER);
    if (user!=null){
        ServerResponse<List<User>> userList=iUserService.selectAll();

        return userList;
    }

    return ServerResponse.createByErrorMessage("用户未登录，无法获取信息");

}

}
