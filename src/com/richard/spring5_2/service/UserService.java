package com.richard.spring5_2.service;

import com.richard.spring5_2.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

// 注解中的value属性值可以省略不写
// 默认值是类名称，首字母小写
//@Component(value = "userService") // 等于 <bean id="" class="" />
@Service
public class UserService {

    @Value(value = "richard")
    private String name;

//    @Autowired
//    @Qualifier(value = "userDaoImpl")
//    private UserDao userDao;

    //@Resource                         //根据类型注入
    @Resource(name = "userDaoImpl")     //根据名称注入
    private UserDao userDao;

    public void add() {
        System.out.println("service add test..." + name);
        userDao.add();
    }
}
