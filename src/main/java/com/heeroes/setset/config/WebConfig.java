package com.heeroes.setset.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.heeroes.setset.interceptor.AuthInterceptor;

import lombok.RequiredArgsConstructor;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.heeroes.setset.interceptor.AuthInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@MapperScan(basePackages = "com.heeroes.setset.*.model.mapper")
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer{
	private final AuthInterceptor authInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry
		.addInterceptor(authInterceptor)
		.addPathPatterns("/*");
	}

}
