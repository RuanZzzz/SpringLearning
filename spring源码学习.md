spring源码学习



[TOC]



# spring框架概述

1、IOC：控制反转，把创建对象过程交给spring进行管理

2、AOP：面向切面，不修改源代码进行功能增强



# spring框架下载

1、网址：https://spring.io/projects/spring-framework#learn

2、框架版本，选择GA

![](https://raw.githubusercontent.com/RuanZzzz/SpringLearning/master/spring%E6%BA%90%E7%A0%81%E5%AD%A6%E4%B9%A0%E7%B4%A0%E6%9D%90/spring%E6%A1%86%E6%9E%B6%E7%89%88%E6%9C%AC%E9%80%89%E6%8B%A9.png)

3、下载地址：https://repo.spring.io/artifactory/release/org/springframework/spring/



# spring源码学习



## 入门

1、创建一个新的Java工程



2、导入spring相关的jar包

包括：Beans、Core、Context、Expression

![](https://github.com/RuanZzzz/SpringLearning/blob/master/spring%E6%BA%90%E7%A0%81%E5%AD%A6%E4%B9%A0%E7%B4%A0%E6%9D%90/%E9%9C%80%E8%A6%81%E7%94%A8%E5%88%B0%E7%9A%84jar%E5%8C%85.png?raw=true)

①、file—>project structure

②、接下来如下图所示

![](https://github.com/RuanZzzz/SpringLearning/blob/master/spring%E6%BA%90%E7%A0%81%E5%AD%A6%E4%B9%A0%E7%B4%A0%E6%9D%90/%E5%AF%BC%E5%85%A5jar%E5%8C%85.png?raw=true)



3、创建普通类，在这个类中创建普通方法

```java
public class User {
    public void add() {
        System.out.println("add...");
    }
}
```



4、创建spring配置文件，在配置文件配置创建的对象

（1）spring配置文件使用xml格式

![](https://github.com/RuanZzzz/SpringLearning/blob/master/spring%E6%BA%90%E7%A0%81%E5%AD%A6%E4%B9%A0%E7%B4%A0%E6%9D%90/%E5%88%9B%E5%BB%BAxml%E6%96%87%E4%BB%B6.png?raw=true)

标签中的内容：

```java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- 配置User对象创建 -->
    <bean id="user" class="com.richard.spring5.User"></bean>

</beans>
```



5、测试代码编写

```java
@Test
public void testAdd() {
    // 1、加载spring的配置文件
    ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");

    // 2、获取配置创建的对象
    User user = context.getBean("user", User.class);

    System.out.println(user);
    user.add();
}
```



# IOC容器

## IOC底层原理

1、IOC的概念

（1）控制反转，把对象创建和对象之间的调用过程，交给spring进行管理

（2）使用IOC目的：为了耦合度降低



2、IOC底层原理

（1）xml解析、工厂模式、反射



目的：耦合度降低到最低限度



## IOC接口（BeanFactory）





## IOC操作Bean管理（基于XML）



## IOC操作Bean管理（基于注解）





# 杂记

1、 `Context` ：上下文

2、`Expression`：表达式
