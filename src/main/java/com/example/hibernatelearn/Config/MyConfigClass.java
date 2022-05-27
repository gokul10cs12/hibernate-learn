package com.example.hibernatelearn.Config;

import myPackage.MyTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfigClass {
    @Bean
    MyTest getMyTest(){
        return new MyTest();
    }
}
