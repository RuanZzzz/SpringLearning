package com.richard.spring5_3.aopanno;

import org.springframework.stereotype.Component;

/**
 * 被增强类
 */
@Component
public class User {
    public void add() {
        System.out.println("add ...");
    }
}
