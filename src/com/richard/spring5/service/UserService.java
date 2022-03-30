package com.richard.spring5.service;

import com.richard.spring5.dao.UserDao;
import com.richard.spring5.dao.UserDaoImpl;

public class UserService {
    // 创建UserDao类型属性，生成set方法
    private UserDao userDao;
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    public void add() {
        System.out.println("service add...");
        userDao.update();
    }
}
