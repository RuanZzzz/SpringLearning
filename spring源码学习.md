spring源码学习



[TOC]



# spring框架概述

1、IOC：控制反转，把创建对象过程交给spring进行管理

2、AOP：面向切面，不修改源代码进行功能增强



# spring框架下载

1、网址：https://spring.io/projects/spring-framework#learn

2、框架版本，选择GA

![](E:\ruanshaoxiang\java\SpringLearning\spring源码学习素材\spring框架版本选择.png)

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

1、IOC思想基于IOC容器完成，IOC容器底层就是对象工厂



2、Spring提供IOC容器实现的两种方式：（两个接口）

（1）BeanFactory：IOC容器基本实现，是Spring内部的使用接口，不提供外部使用

①、加载配置文件时不会创建对象，在获取对象（使用）才会去创建对象



（2）ApplicationContext：BeanFactory接口的子接口，提供了更多更强大的功能，一般供外部使用

①、加载配置文件时就会把配置文件中的对象进行创建



3、ApplicationContext接口中的实现类

![](E:\ruanshaoxiang\java\SpringLearning\spring源码学习素材\两个比较重要的实现类.png)

`classPathXmlApplicationContext`：src下的类路径

`FileSystemXmlApplicationContext`：绝对路径（如：某个磁盘下的文件）





## IOC操作Bean管理

1、Bean管理的概念：Bean管理涉及的两个操作

（1）Spring创建对象

（2）Spring注入属性



2、Bean管理操作的两种方式：

（1）基于xml配置文件方式实现

（2）基于注解方式实现



### 基于XML配置文件方式

1、**基于xml方式创建对象**

（1）在spring配置文件中，使用bean标签，标签里添加对应属性，就可以实现对象的创建（bean1.xml）

```xml
<!-- 配置User对象创建 -->
<bean id="user" class="com.richard.spring5.User"></bean>
```

（2）bean标签中常用的属性

①、`id属性`：对象对应的唯一表示

②、`class属性`：对象所在的位置（类全路径）

（3）创建对象的时候，默认执行无参构造方法完成对象创建



2、**基于xml方式注入属性**

（1）DI：依赖注入，也是注入属性（DI是IOC中的一种具体实现）



2.1. 第一种注入方式：使用set方法进行注入

（1）创建类，定义属性和对应的set方法

```java
public class Book {
    // 创建属性
    private String bookName;
    private String author;
    // 创建属性对应的set方法
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public static void main(String[] args) {
        Book book = new Book();
        book.setBookName("laravel");
        book.setAuthor("Richard");
    }
}
```

（2）在spring配置文件配置对象创建，配置属性注入（bean1.xml）

```xml
<bean id="book" class="com.richard.spring5.Book">
    <!-- 使用property完成属性注入
             name：类中属性的名称
             value：向属性注入的值（执行set方法）
        -->
    <property name="bookName" value="SpringBoot"></property>
    <property name="author" value="RuanZzzz"></property>
</bean>
```

（3）测试

```java
@Test
public void testBook1() {
    // 1、加载spring的配置文件
    ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
    // 2、获取配置创建的对象
    Book book = context.getBean("book",Book.class);
    System.out.println(book);
    book.testDemo();	// 打印结果——> SpringBoot：RuanZzzz
}
```



2.2. 第二种注入方式：有参构造注入

（1）创建类，定义属性，创建属性对应有参数构造

```java
public class Orders {
    // 属性
    private String orderName;
    private String address;
    // 有参构造
    public Orders(String orderName, String address) {
        this.orderName = orderName;
        this.address = address;
    }
    public void orderTest() {
        System.out.println(orderName + "：" + address);
    }
}
```

（2）在spring配置文件中进行配置（bean1.xml）

```xml
<bean id="orders" class="com.richard.spring5.Orders">
    <constructor-arg name="orderName" value="Yeezy 350"></constructor-arg>
    <constructor-arg name="address" value="四川成都"></constructor-arg>
</bean>
```

（3）测试

```java
@Test
public void testOrders() {
    // 1、加载spring的配置文件
    ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
    // 2、获取配置创建的对象
    Orders orders = context.getBean("orders",Orders.class);
    System.out.println(orders);
    orders.orderTest();		// 打印结果——> Yeezy 350：四川成都
}
```



2.3. P名称空间注入（仅了解）

（1）使用P名称空间注入，可以简化基于xml配置

（2）添加p名称空间在配置文件中

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"  <!-- 这里 -->
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
```

（3）进行属性注入，在bean标签里

```xml
<!-- set方法注入属性 -->
<bean id="book" class="com.richard.spring5.Book" p:bookName="Spring技术内幕" p:author="机械工业出版社"></bean>
```



#### XML注入其他类型属性

1、字面量（即为属性赋值，如：`private String bookName = "Spring技术内幕";`）

（1）设置属性值为null值

```xml
<bean id="book" class="com.richard.spring5.Book">
    <property name="bookName" value="SpringBoot"></property>
    <property name="author" value="RuanZzzz"></property>
    <!-- 设置null值 -->
    <property name="address" >
        <null />
    </property>
</bean>
```

测试：

```java
@Test
public void testBook1() {
    ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
    Book book = context.getBean("book",Book.class);
    System.out.println(book);
    book.testDemo();	// 打印结果——> SpringBoot：RuanZzzz：null
}
```



（2）设置属性值包含特殊符号

```xml
<bean id="book" class="com.richard.spring5.Book">
    <property name="bookName" value="SpringBoot"></property>
    <property name="author" value="RuanZzzz"></property>
    <!-- 属性值中包含特殊符号，如：<>
            1、把<>进行转义 &lt; &gt;
            2、把带特殊符号内容写到CDATA
        -->
    <property name="address">
        <value><![CDATA[<<四川成都>>]]></value>
    </property>
</bean>
```

测试：

```java
@Test
public void testBook1() {
    ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
    Book book = context.getBean("book",Book.class);
    System.out.println(book);
    book.testDemo();	// 打印结果——> SpringBoot：RuanZzzz：<<四川成都>>
}
```



2、注入属性—外部bean（比如：web层 调用 service层 调用 DO层）

（1）创建两个类service类和dao类

（2）在service调用dao里面的方法

（3）在spring配置文件中进行配置

service：

```java
public class UserService {
    // 创建UserDao类型属性，生成set方法
    private UserDao userDao;
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    public void add() {
        System.out.println("service add...");
        userDao.update();
    }
}
```

配置文件：实现外部bean的注入（bean2.xml）

```xml
<!-- service和dao对象创建 -->
<bean id="userService" class="com.richard.spring5.service.UserService">
    <!-- 注入userDao对象
            name属性：类里面的属性名称
            ref属性：创建userDao对象bean标签id值
        -->
    <property name="userDao" ref="userDaoImpl"></property>
</bean>
<bean id="userDaoImpl" class="com.richard.spring5.dao.UserDaoImpl"></bean>
```

测试：

```java
@Test
public void testBean() {
    ApplicationContext context = new ClassPathXmlApplicationContext("bean2.xml");
    UserService userService = context.getBean("userService",UserService.class);
    userService.add();
}
```



3、注入属性—内部bean

（1）一对多关系

（2）在实体类之间表示一对多关系

```java
// 部门类
public class Dept {
    // 部门名称
    private String dname;
    public void setDname(String dname) {
        this.dname = dname;
    }
    @Override
    public String toString() {
        return "Dept{" +
                "dname='" + dname + '\'' +
                '}';
    }
}
```

```java
// 员工类
public class Emp {
    // 员工名称
    private String ename;
    // 性别
    private String gender;
    // 员工属于某个部门，使用对象形式表示
    private Dept dept;
    
    public Dept getDept() {
        return dept;
    }
    public void setDept(Dept dept) {
        this.dept = dept;
    }
    public void setEname(String ename) {
        this.ename = ename;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void add() {
        System.out.println(ename + "：" + gender + "：" + dept);
    }
}
```

（3）在spring配置文件中进行配置（bean3）

```xml
<!-- 内部bean -->
<bean id="emp" class="com.richard.spring5.bean.Emp">
    <!-- 先设置两个普通属性 -->
    <property name="ename" value="richard"></property>
    <property name="gender" value="男"></property>
    <!-- 设置对象类属性 -->
    <property name="dept">
        <bean id="dept" class="com.richard.spring5.bean.Dept">
            <property name="dname" value="艾欧尼亚"></property>
        </bean>
    </property>
</bean>
```

（4）测试

```java
@Test
public void testBean2() {
    ApplicationContext context = new ClassPathXmlApplicationContext("bean3.xml");
    Emp emp = context.getBean("emp", Emp.class);
    emp.add();	// 打印结果——> richard：男：Dept{dname='艾欧尼亚'}
}
```



4、注入属性—级联赋值

（1）第一种写法

```xml
<!-- 级联赋值 -->
<bean id="emp" class="com.richard.spring5.bean.Emp">
    <!-- 先设置两个普通属性 -->
    <property name="ename" value="richard"></property>
    <property name="gender" value="男"></property>
    <!-- 级联赋值 -->
    <property name="dept" ref="dept"></property>
</bean>
<bean id="dept" class="com.richard.spring5.bean.Dept">
    <property name="dname" value="诺克萨斯"></property>
</bean>
```

（2）第二种写法

```xml
 <!-- 级联赋值 -->
<bean id="emp" class="com.richard.spring5.bean.Emp">
    <!-- 先设置两个普通属性 -->
    <property name="ename" value="richard"></property>
    <property name="gender" value="男"></property>
    <!-- 级联赋值 -->
    <property name="dept" ref="dept"></property>
    <property name="dept.dname" value="黑色玫瑰"></property>
</bean>
```





### 基于注解方式





# 杂记

1、 `Context` ：上下文

2、`Expression`：表达式
