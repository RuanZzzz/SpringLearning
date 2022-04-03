package com.richard.spring5_1.testdemo;

import com.richard.spring5_1.collectiontype.Stu;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpring5Demo1 {

    @Test
    public void testCollection() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean5.xml");
        Stu stu = context.getBean("stu",Stu.class);
        stu.test();
    }

}
