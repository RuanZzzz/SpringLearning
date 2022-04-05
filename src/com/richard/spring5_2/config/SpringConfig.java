package com.richard.spring5_2.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration // 作为配置类，替代xml配置文件
@ComponentScan(basePackages = {"com.richard.spring5_2"})
public class SpringConfig {
}
