package com.chieftain.agile;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ServletComponentScan
@EnableWebMvc
@EnableTransactionManagement
@EnableCaching
@MapperScan(basePackages = "com.chieftain.agile.dao")
public class JAgileKitApplication {

	public static void main(String[] args) {
        SpringApplication.run(JAgileKitApplication.class, args);
	}
}
