package com.richard.spring5_3.test;

import com.richard.spring5_3.aopanno.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAop {

    @Test
    public void testAopAnno() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean12.xml");
        User user = context.getBean("user", User.class);
        user.add();
    }

}
