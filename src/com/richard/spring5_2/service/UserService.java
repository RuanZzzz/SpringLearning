package com.richard.spring5_2.service;

import org.springframework.stereotype.Component;

// 注解中的value属性值可以省略不写
// 默认值是类名称，首字母小写
@Component(value = "userService") // 等于 <bean id="" class="" />
public class UserService {
    public void add() {
        System.out.println("service add...");
    }
}
