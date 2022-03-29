package com.richard.spring5.testdemo;

import com.richard.spring5.User;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpring5 {

    @Test
    public void testAdd() {
        // 1、加载spring的配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
        //BeanFactory context = new ClassPathXmlApplicationContext("bean1.xml");

        // 2、获取配置创建的对象
        User user = context.getBean("user", User.class);

        System.out.println(user);
        user.add();
    }

}
