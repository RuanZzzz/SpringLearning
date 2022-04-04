package com.richard.spring5_2.service;

import com.richard.spring5_2.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

// 注解中的value属性值可以省略不写
// 默认值是类名称，首字母小写
//@Component(value = "userService") // 等于 <bean id="" class="" />
@Service
public class UserService {
    @Autowired
    @Qualifier(value = "userDaoImpl")
    private UserDao userDao;

    public void add() {
        System.out.println("service add test...");
        userDao.add();
    }
}
