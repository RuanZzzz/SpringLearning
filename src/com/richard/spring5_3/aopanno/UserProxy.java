package com.richard.spring5_3.aopanno;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

// 增强的类
@Component
@Aspect     // 生成代理对象
@Order(2)
public class UserProxy {

    // 相同切入点抽取
    @Pointcut(value = "execution(* com.richard.spring5_3.aopanno.User.add(..))")
    public void pointDemo() {

    }

    // 前置通知
    // @Before注解表示作为前置通知
    @Before(value = "pointDemo()")
    public void before() {
        System.out.println("before ...");
    }

    // 后置通知（返回通知）
    @AfterReturning(value = "execution(* com.richard.spring5_3.aopanno.User.add(..))")
    public void afterReturning() {
        System.out.println("afterReturning ...");
    }

    // 最终通知
    @After(value = "execution(* com.richard.spring5_3.aopanno.User.add(..))")
    public void after() {
        System.out.println("after ...");
    }

    @AfterThrowing(value = "execution(* com.richard.spring5_3.aopanno.User.add(..))")
    public void afterThrowing() {
        System.out.println("afterThrowing ...");
    }

    @Around(value = "execution(* com.richard.spring5_3.aopanno.User.add(..))")
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("环绕之前 ...");

        // 陪增强的方法的执行
        proceedingJoinPoint.proceed();

        System.out.println("环绕之后 ...");
    }
}
