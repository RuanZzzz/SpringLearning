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

![](E:\ruanshaoxiang\java\SpringLearning\spring源码学习素材\需要用到的jar包.png)

①、file—>project structure

②、接下来如下图所示

![](E:\ruanshaoxiang\java\SpringLearning\spring源码学习素材\导入jar包.png)

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

![](E:\ruanshaoxiang\java\SpringLearning\spring源码学习素材\创建xml文件.png)

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



原始模式、工厂模式

![](E:\ruanshaoxiang\java\SpringLearning\spring源码学习素材\IOC底层（原始方式、工厂模式）.png)



**<font color=red>IOC过程</font>**：

第一步 xml配置文件，配置创建的对象

```xml
<bean id="dao" class="com.richard.UserDao"></bean>
```

第二步 有service类和dao类，创建工厂类

```java
class UserFactory {
    public static UserDao getDao() {
        // 1、xml解析
        String classValue = class属性值 
        // 2、通过反射创建对象 
        Class clazz = Class.forName(classValue);
        return (UserDao)clazz.newInstance();
    }
}
```

目的：进一步降低耦合度



## IOC接口（BeanFactory）





## IOC操作Bean管理（基于XML）



## IOC操作Bean管理（基于注解）





# 杂记

1、 `Context` ：上下文

2、`Expression`：表达式
