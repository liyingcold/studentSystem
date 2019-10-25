package com.ly.common;

public class Const {
//    登陆用户存入session的键值
    public static final String CURRENT_USER="currentUser";

    public static final String EMAIL="email";
    public static final String USERNAME="username";

//    角色
    public interface Role{
        int ROLE_CUSTOMER=2;//用户
        int ROLE_ADMIN=1;//管理员
}
}
