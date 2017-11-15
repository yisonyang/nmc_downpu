package com.downpu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
public class DownpuApplication {

	public static void main(String[] args) {
		SpringApplication.run(DownpuApplication.class, args);
	}
	@Bean
	public MultipartConfigElement multipartConfigElement(){
		MultipartConfigFactory factory=new MultipartConfigFactory();
		factory.setMaxFileSize("102400MB");
		factory.setMaxRequestSize("102400MB");
		return factory.createMultipartConfig();
	}
}
