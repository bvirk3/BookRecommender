package com.bhanu.book.controllers;


 
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
 
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.bhanu.book.controllers")
public class HelloWorldConfiguration {
     
 
}
