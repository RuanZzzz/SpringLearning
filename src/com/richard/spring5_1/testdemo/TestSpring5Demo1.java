package com.richard.spring5_1.testdemo;

import com.richard.spring5_1.autowire.Emp;
import com.richard.spring5_1.bean.Orders;
import com.richard.spring5_1.collectiontype.Book;
import com.richard.spring5_1.collectiontype.Course;
import com.richard.spring5_1.collectiontype.Stu;
import com.richard.spring5_1.factorybean.MyBean;
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

    @Test
    public void testCollection2() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean6.xml");
        Book book1 = context.getBean("book",Book.class);
        Book book2 = context.getBean("book",Book.class);
        //book.test();
        System.out.println(book1);
        System.out.println(book2);
    }

    @Test
    public void test3() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean7.xml");
        Course course = context.getBean("myBean", Course.class);
        System.out.println(course);
    }

    @Test
    public void testBean() {
        //ApplicationContext context = new ClassPathXmlApplicationContext("bean8.xml");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean8.xml");
        Orders orders = context.getBean("orders",Orders.class);
        System.out.println("第四步 获取创建bean实例对象");
        System.out.println(orders);

        // 手动让bean实例销毁
        context.close();
    }

    @Test
    public void test4() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean9.xml");
        Emp emp = context.getBean("emp",Emp.class);
        System.out.println(emp);
    }

}
