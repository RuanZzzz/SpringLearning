package com.richard.spring5_3.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = {"com.richard"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ConfigAop {
}
