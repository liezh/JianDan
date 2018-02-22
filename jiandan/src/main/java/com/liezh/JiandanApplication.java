package com.liezh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//// 配置mybatis包扫描路径
@MapperScan("com.liezh.dao")
public class JiandanApplication {

	public static void main(String[] args) {
		SpringApplication.run(JiandanApplication.class, args);
	}
}
