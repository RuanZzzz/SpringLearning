package com.richard.spring5_3.test;

import com.richard.spring5_3.aopanno.User;
import com.richard.spring5_3.aopxml.Book;
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


    @Test
    public void testAopXml() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean13.xml");
        Book book = context.getBean("book", Book.class);
        book.buy();
    }

}
