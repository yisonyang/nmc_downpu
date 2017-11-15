package com.downpu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import javax.servlet.MultipartConfigElement;

/**
 * Created by yy187 on 2017/9/21.
 */
public class Application extends SpringBootServletInitializer {
   @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
       return builder.sources(DownpuApplication.class);
   }
}
