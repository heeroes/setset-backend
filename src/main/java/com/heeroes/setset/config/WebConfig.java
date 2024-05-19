package com.heeroes.setset.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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
		.addPathPatterns("/**")
		.excludePathPatterns("/user/login/**")
		.excludePathPatterns("/safe/**")
		.excludePathPatterns("/attraction/**");
	}
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry
				.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(),
						HttpMethod.DELETE.name(), HttpMethod.HEAD.name(), HttpMethod.OPTIONS.name(),
						HttpMethod.PATCH.name())
				.maxAge(1800); // Pre-flight Caching
	}

}
