package com.heeroes.setset.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.heeroes.setset.*.model.mapper")
public class WebConfig {

}
