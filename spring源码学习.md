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



### 外部属性文件

1、直接配置数据库信息

（1）配置德鲁伊连接池

（2）引入德鲁伊连接池依赖 jar 包— `druid-1.1.9.jar`

（3）配置信息（bean10）

```xml
<!-- 直接配置连接池 -->
<bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
    <property name="driverClassName" value="com.mysql.jdbc.Driver"></property>
    <property name="url" value="jdbc:mysql://localhost:3306/wiki"></property>
    <property name="username" value="ruanshaoxiang"></property>
    <property name="password" value="123456"></property>
</bean>
```



2、引入外部属性文件配置数据库连接池

（1）创建外部属性文件，格式为properties文件，存储数据库信息

```properties
prop.driverClass=com.mysql.jdbc.Driver
prop.url=jdbc:mysql://localhost:3306/wiki
prop.userName=ruanshaoxiang
prop.password=123456
```

（2）将外部properties属性文件引入到spring配置文件中

*引入context名称空间（bean10）

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context" 
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                          http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
</beans>
```

主要内容为：

> xmlns:context="http://www.springframework.org/schema/context" 
>
> http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd

在spring配置文件使用标签引入外部属性文件（bean10）

```xml
<!-- 引入外部属性文件 -->
<context:property-placeholder location="classpath:jdbc.properties" />

<!-- 直接配置连接池 -->
<bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
    <property name="driverClassName" value="${prop.driverClass}"></property>
    <property name="url" value="${prop.url}"></property>
    <property name="username" value="${prop.userName}"></property>
    <property name="password" value="${prop.password}"></property>
</bean>
```





### FactoryBean

1、Spring有两种类型bean，一种普通bean，另一种工厂bean（FactoryBean）



2、普通Bean：在配置文件中定义bean类型就是返回类型

比如：在配置文件种为

```xml
<bean id="book" class="com.richard.spring5_1.collectiontype.Book"></bean>
```

那在实际使用中，就只能返回对应的类型

```java
ApplicationContext context = new ClassPathXmlApplicationContext("bean6.xml");
Book book = context.getBean("book",Book.class);
```



3、工厂Bean：在配置文件种定义bean类型可以和返回类型不一样

第一步 创建类，让该类作为工厂bean，实现接口FactoryBean

第二部 实现接口里面的方法，在实现的方法中定义返回的bean类型

```java
public class MyBean implements FactoryBean<Course> {
    // 定义返回bean
    @Override
    public Course getObject() throws Exception {
        Course course = new Course();
        course.setCname("test");

        return course;
    }
    @Override
    public Class<?> getObjectType() {
        return null;
    }
    @Override
    public boolean isSingleton() {
        return false;
    }
}
```





### Bean 作用域

1、在Spring里面，设置创建bean实例是单实例还是多实例

2、在Spring里面，默认情况下，bean是单实例对象

单例：

```java
public void testCollection2() {
    ApplicationContext context = new ClassPathXmlApplicationContext("bean6.xml");
    Book book1 = context.getBean("book",Book.class);
    Book book2 = context.getBean("book",Book.class);
    //book.test();
    System.out.println(book1);
    System.out.println(book2);
}
```

地址输出相同

> com.richard.spring5_1.collectiontype.Book@1a18f1b
>
> com.richard.spring5_1.collectiontype.Book@1a18f1b



3、如何设置单实例还是多实例

（1）在Spring配置文件bean标签里面有属性（scope）用于设置单实例还是多实例

（2）scope属性值

第一个值：默认值 `singleton`，表示单实例对象

第二个值：`prototype`，表示多实例对象（bean6）

```xml
<bean id="book" class="com.richard.spring5_1.collectiontype.Book" scope="prototype">
    <property name="list" ref="bookList"></property>
</bean>
```

这时候输出的地址就不相同了

（3）singleton和prototype区别

①、singleton是单例，prototype是多例

②、设置scope值是singleton时候，加载spring配置文件时候就会创建单实例对象

​        设置scope值是prototype时候，不是在加载spring配置文件时候创建对象，而是在调用 `getBean` 获取创建对象的时候创建多实例对象



### Bean 生命周期（重要）

1、生命周期

（1）从对象的创建到对象销毁的过程



2、bean生命周期

（1）通过构造器创建bean实例（无参数构造器）

（2）为bean的属性设置值和对其他bean的引用（调用其对应的set方法）

（3）调用bean的初始化的方法（需要进行配置）

（4）bean这时就可以使用了（对象获取到了）

（5）当容器关闭时候，调用bean的销毁的方法（需要进行配置销毁的方法）



#### **<font color=red>bean生命周期的实现</font>**

类：

```java
public class Orders {
    // 无参构造器
    public Orders() {
        System.out.println("第一步 执行无参数构造器创建bean实例");
    }
    private String oname;
    public void setOname(String oname) {
        this.oname = oname;
        System.out.println("第二步 调用set方法设置属性值");
    }
    // 创建执行初始化方法
    public void initMethod() {
        System.out.println("第三步 执行初始化方法");
    }
    // 创建执行销毁的方法
    public void destroyMethod() {
        System.out.println("第五步 执行销毁的方法");
    }
}
```

配置文件（bean8）：

```xml
<bean id="orders" class="com.richard.spring5_1.bean.Orders" init-method="initMethod" destroy-method="destroyMethod">
    <property name="oname" value="yeezy350 白斑马"></property>
</bean>
```

测试方法：

```java
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
```

执行结果：

> 第一步 执行无参数构造器创建bean实例
> 第二步 调用set方法设置属性值
> 第三步 执行初始化方法
> 第四步 获取创建bean实例对象
> com.richard.spring5_1.bean.Orders@e6aa2
> 第五步 执行销毁的方法	



#### **<font color=red>bean的后置处理器</font>**

1、（在生命周期的基础上，还有另外两步）——七步

（1）通过构造器创建bean示例（无参数构造器）

（2）为bean的属性设置值和对其他bean的引用（调用其对应的set方法）

（3）把bean实例传递给bean后置处理的方法 `postProcessBeforeInitialization`

（4）**<font color=red>调用bean的初始化的方法（需要进行配置）</font>**——**原来的第三步**

（5）把bean实例传递给bean后置处理的方法 `postProcessAfterInitialization`

（6）bean这时就可以使用了（对象获取到了）

（7）当容器关闭时候，调用bean的销毁的方法（需要进行配置销毁的方法）



2、演示添加后置处理器效果

（1）创建类，实现接口 BeanPostProcessor，创建后置处理器

```java
public class MyBeanPost implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("在初始化之前执行的方法");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("在初始化之后执行的方法");
        return bean;
    }
}
```

（2）同时需要在配置文件进行配置（也在bean8）

```xml
<!-- 配置后置处理器 -->
<bean id="myBeanPost" class="com.richard.spring5_1.bean.MyBeanPost"></bean>
```

（3）执行上面的测试方法，结果

> 第一步 执行无参数构造器创建bean实例
> 第二步 调用set方法设置属性值
> 在初始化之前执行的方法
> 第三步 执行初始化方法
> 在初始化之后执行的方法
> 第四步 获取创建bean实例对象
> com.richard.spring5_1.bean.Orders@1b64261
> 第五步 执行销毁的方法	



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



#### XML注入集合属性（重要）

1、注入数组类型属性

2、注入List集合类型属性

3、注入Map集合类型属性

（1）创建类，并定义数组类型：list、map、set类型属性

```java
public class Stu {
    // 1 数组类型属性
    private String[] courses;
    // 2 list集合类型属性
    private List<String> list;
    // 3 map集合类型属性
    private Map<String,String> maps;
    // 4 set集合类型属性
    private Set<String> sets;
    
    public void setCourses(String[] courses) {
        this.courses = courses;
    }
    public void setList(List<String> list) {
        this.list = list;
    }
    public void setMaps(Map<String, String> maps) {
        this.maps = maps;
    }
    public void setSets(Set<String> sets) {
        this.sets = sets;
    }
}
```

（2）在spring配置文件进行配置（bean5）

```xml
<bean id="stu" class="com.richard.spring5_1.collectiontype.Stu">
    <!-- 数组类型属性注入 -->
    <property name="courses">
        <array>
            <value>C语言</value>
            <value>Laravel框架</value>
            <value>Spring技术内幕</value>
            <value>数据库</value>
        </array>
    </property>
    <!-- list类型属性注入 -->
    <property name="list">
        <list>
            <value>软烧香</value>
            <value>庒易得</value>
            <value>陈另类</value>
        </list>
    </property>
    <!-- map类型属性注入 -->
    <property name="maps">
        <map>
            <entry key="C" value="c"></entry>
            <entry key="Laravel" value="laravel"></entry>
            <entry key="Spring" value="spring"></entry>
        </map>
    </property>
    <!-- set类型属性注入 -->
    <property name="sets">
        <set>
            <value>Mysql</value>
            <value>Redis</value>
        </set>
    </property>
</bean>
```

（3）测试

```java
@Test
public void testCollection() {
    ApplicationContext context = new ClassPathXmlApplicationContext("bean5.xml");
    Stu stu = context.getBean("stu",Stu.class);
    stu.test();
}
```

4、在集合里面设置对象类型值

（1）创建多个对象（bean5）

```XML
<!-- 创建多个course对象 -->
<bean id="course1" class="com.richard.spring5_1.collectiontype.Course">
    <property name="cname" value="Spring5框架"></property>
</bean>
<bean id="course2" class="com.richard.spring5_1.collectiontype.Course">
    <property name="cname" value="MyBatis框架"></property>
</bean>
```

（2）进入注入

```xml
<!-- 注入list集合类型，值是对象 -->
<property name="courseList">
    <list>
        <ref bean="course1"></ref>
        <ref bean="course2"></ref>
    </list>
</property>
```



5、把集合注入部分提取出来

（1）在spring配置文件中引入名称空间 util（bean6）

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:util="http://www.springframework.org/schema/util"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd">


</beans>
```

（2）使用util标签完成list集合注入提取（bean6）

```xml
<!-- 1、提取list集合类型属性注入 -->
<util:list id="bookList">
    <value>Spring技术内幕</value>
    <value>laravel框架</value>
    <value>Redis集群</value>
</util:list>

<!-- 2、提取list集合类型属性注入使用 -->
<bean id="book" class="com.richard.spring5_1.collectiontype.Book">
    <property name="list" ref="bookList"></property>
</bean>
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



#### 自动装配

1、概念

（1）根据指定装配规则（属性名称或属性类型），Spring自动将匹配的属性值进行注入



2、自动装配的过程

（1）根据属性名称自动注入（bean9）

```xml
<!--实现自动装配
    bean标签属性autowire：配置自动装配
    autowire属性常用的两个值：
      ①、byName：根据属性名称注入，待注入bean的id值要和类属性名称一样
      ②、byType：根据属性类型注入
-->
<bean id="emp" class="com.richard.spring5_1.autowire.Emp" autowire="byName"></bean>
<bean id="dept" class="com.richard.spring5_1.autowire.Dept"></bean>
```

在 `emp` 类中为

```java
public class Emp {
    private Dept dept;
    public void setDept(Dept dept) {
        this.dept = dept;
    }
}
```

（2）根据属性类型自动注入（bean9）

```xml
<bean id="emp" class="com.richard.spring5_1.autowire.Emp" autowire="byType"></bean>
```





### 基于注解方式

1、概念

（1）注解是代码特殊标记，格式：@注解名称（属性名称=属性值，属性名称=属性...）

（2）使用注解：注解可以作用在类上面、方法上面、属性上面

（3）使用注解的目的：为了简化xml配置



2、Spring针对Bean管理中创建对象提供注解

（1）@Component

（2）@Service

（3）@Controller

（4）@Repository

以上四个注解功能都是一样的，用来创建bean对象



3、基于注解方式实现对象创建

第一步 引入依赖 `spring-aop-5.2.6.RELEASE.jar`

第二步 开启组件扫描（bean11）

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
    <!-- 开启组件扫描
         扫描多个包:① 可以使用逗号隔开
                  ② 扫描包的上层目录（都在同一个包的情况下）
     -->
    <context:component-scan base-package="com.richard.spring5_2"></context:component-scan>
</beans>
```

第三步 创建类，并在类上面添加创建对象的注解

```java
// 注解中的value属性值可以省略不写
// 默认值是类名称，首字母小写
@Component(value = "userService") // 等于 <bean id="" class="" />
public class UserService {
    public void add() {
        System.out.println("service add...");
    }
}
```

测试：

```java
@Test
public void testService() {
    ApplicationContext context = new ClassPathXmlApplicationContext("bean11.xml");
    UserService userService = context.getBean("userService", UserService.class);
    System.out.println(userService);
    userService.add();
}
```



4、开启组件扫描细节配置（bean11）

```xml
<!-- 示例1
    use-default-filters="false" 表示现在不使用默认filter，而是使用自己配置的filter
    context:include-filter 设置扫描指定的内容
    以下的内容为，只扫描这个包下面带注解 @Controller 的类
-->
<context:component-scan base-package="com.richard.spring5_2" use-default-filters="false">
    <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
</context:component-scan>
```

```xml
<!-- 示例2
    context:exclude-filter：设置哪些类不要扫描
    以下的内容为：扫描包下面除了注解@Controller以外的类
 -->
<context:component-scan base-package="com.richard.spring5_2">
    <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
</context:component-scan>
```



5、基于注解方式实现属性注入

（1）@AutoWired：根据属性类型进行自动装配

第一步 把service和dao对象进行创建，在service和dao类添加创建对象注解

```java
public interface UserDao {
    public void add();
}
```

```java
@Repository
public class UserDaoImpl implements UserDao{
    @Override
    public void add() {
        System.out.println("dao add");
    }
}
```

第二步 在service注入dao对象，在service类添加dao类型属性，在属性上面使用注解

```java
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public void add() {
        System.out.println("service add test...");
        userDao.add();
    }
}
```



（2）@Qualifier：根据属性名称进行注入

该注解@Qualifier的使用，**<font color=red>需要和上面@AutoWired一起使用</font>**，指定使用的类（存在一个接口多个实现类）

```java
@Autowired
@Qualifier(value = "userDaoImpl")
private UserDao userDao;
```



（3）@Resource：可以根据类型注入，也可以根据名称注入

```java
//@Resource                         //根据类型注入
@Resource(name = "userDaoImpl")     //根据名称注入
private UserDao userDao;
```



（4）@Value：注入普通类型属性

```java
@Value(value = "richard")
private String name;
```





6、**<font color=red>完全注解开发</font>**

（1）创建配置类，替代xml配置文件

```java
@Configuration // 作为配置类，替代xml配置文件
@ComponentScan(basePackages = {"com.richard.spring5_2"})
public class SpringConfig {
}
```

（2）编写测试类

```java
@Test
public void testService1() {
    // 加载配置类
    ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
    UserService userService = context.getBean("userService", UserService.class);
    System.out.println(userService);
    userService.add();
}
```





# AOP

## 基本概念

1、AOP

（1）面向切面编程，利用AOP可以对业务逻辑的各个部分进行隔离（数据埋点），从而使得业务逻辑各部分之间的耦合度降低，提高程序的可重用性，同时提高了开发的效率

（2）即为：不通过修改源代码的方式，在主干功能里面添加新功能



## 底层原理

1、AOP底层使用动态代理

（1）有两种情况动态代理

**第一种 有接口情况：使用JDK动态代理**

接口：

```java
interface UserDao{
    public void login();
}
```

实现类：

```java
class UserDaoImpl implements UserDao {
    public void login() {
        // 登录实现过程
    }
}
```

JDK动态代理：

创建UserDao接口实现类代理对象，从而完善实现类的方法



**第二种 没有接口情况：使用CGLIB动态代理**

```java
class User {
    public void add() {
        ...
    }
}
```

```java
class Person extends User {
    public void add() {
        super.add();
        // 增强逻辑
    }
}
```



CGLIB动态代理

创建当前类的子类的代理对象，从而完善类中的方法



## JDK动态代理

1、使用JDK动态代理，使用Proxy类里面的方法创建代理对象

java.lang.Object

​		|—java.lang.reflect.Proxy

（1）newProxyInstance方法：返回指定接口的代理类的实例，该接口方法调用分派给指定的调用处理程序

方法的三个参数：

第一个参数：类加载器

第二个参数：增加方法所在的类，这个类实现的接口，支持多个接口

第三个参数：实现这个接口InvocationHandler，创建代理对象，写增强的方法



2、JDK动态代理代码

（1）创建接口，定义方法

```java
public interface UserDao {
    public int add(int a, int b);
    public String update(String id);
}
```

（2）创建接口实现类，实现方法

```java
public class UserDaoImpl implements UserDao{
    @Override
    public int add(int a, int b) {
        return a + b;
    }
    @Override
    public String update(String id) {
        return id;
    }
}
```

（3）使用Proxy类创建接口代理对象

```java
public class JDKProxy {
    public static void main(String[] args) {
        // 创建接口实现类的代理对象
        Class[] interfaces = {UserDao.class};
        UserDaoImpl userDao = new UserDaoImpl();
        UserDao dao = (UserDao) Proxy.newProxyInstance(JDKProxy.class.getClassLoader(),interfaces, new UserDaoProxy(userDao));
        int result = dao.add(1,2);
        System.out.println(result);
    }
}
// 创建代理对象代码
class UserDaoProxy implements InvocationHandler {
    // 1、将需要代理的对象传递进来
    // 有参数构造传递
    private Object obj;
    public UserDaoProxy (Object obj) {
        this.obj = obj;
    }
    // 需要新增逻辑的部分
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 方法之前的处理
        System.out.println("方法之前执行" + method.getName() + "：传递的参数" + Arrays.toString(args));
        // 被增强的方法的执行
        Object res = method.invoke(obj,args);
        // 方法之后的处理
        System.out.println("方法之后执行" + obj);
        return res;
    }
}
```

输出为：

> 方法之前执行add：传递的参数[1, 2]
> add方法执行了
> 方法之后执行com.richard.spring5_3.UserDaoImpl@1716361
> 3



## AOP的操作术语

例子：

```java
class User {
    add();
    update();
    select();
    delete();
}
```

1、连接点

类中可以增强的方法就被称为连接点，如例子中的 `add`、`update`、`select`、`delete`

2、切入点

实际被真正增强的方法，称为切入点（如操作记录，被记录的方法就是切入点）

3、通知（增强）

（1）实际增强的逻辑部分称为通知（比如操作记录，那么操作记录的实现逻辑，就是通知）

（2）通知有多种类型

①、前置通知：被增强的方法执行前

②、后置通知：被增强的方法执行后

③、环绕通知：被增强的方法执行前后

④、异常通知：被增强的方法出现异常后

⑤、最终通知：相当于try...catch...中的finally

4、切面

（1）那通知应用到切入点的过程



## 使用AOP前的准备工作

1、Spring框架一般基于AspectJ实现AOP操作

（1）AspectJ的概念

①、AspectJ不是Spring组成部分，是一个独立AOP框架，一般把AspectJ和Spring框架一起使用，进行AOP操作



2、基于AspectJ实现AOP操作

（1）基于xml配置文件实现

（2）基于注解方式实现（常用）



3、在项目工程里引入AOP相关依赖

`spring-aspects-5.2.6.RELEASE.jar`、`com.springsource.net.sf.cglib-2.2.0.jar`、`com.springsource.org.aopalliance-1.0.0.jar`、`com.springsource.org.aspectj.weaver-1.6.8.RELEASE.jar`、`spring-aop-5.2.6.RELEASE.jar`



4、切入点表达式

（1）切入点表达式作用：知道对哪个类里面的哪个方法进行增强

（2）语法结构：

```java
execution([权限修饰符][返回类型][类全路径][方法名称]([参数列表]))
```

例子1：对 com.richard.dao.BookDao类里面的add方法进行增强

```java
execution(* com.richard.dao.BookDao.add(..))
/*
 * *表示任意修饰符
 * 返回类型可以省略
 * 类全路径：com.richard.dao.BookDao
 * 方法名称：.add()
 * 参数列表：【..】 表示方法中的参数
 */
```

例子2：对 com.richard.dao.BookDao类里面的所有方法进行增强

```java
execution(* com.richard.dao.BookDao.*(..))
/*
 * com.richard.dao.BookDao.* 表示所有方法
 */
```

例子2：对 com.richard.dao包里面的所有类，类里面的所有方法都进行增强

```java
execution(* com.richard.dao.*(..))
```







# 杂记

1、 `Context` ：上下文

2、`Expression`：表达式
