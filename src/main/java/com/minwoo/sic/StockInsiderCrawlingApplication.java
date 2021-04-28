package com.minwoo.sic;

import org.mybatis.spring.annotation.MapperScan; 
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan(value="com.minwoo.sic.dao")
@EnableScheduling
@SpringBootApplication
public class StockInsiderCrawlingApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(StockInsiderCrawlingApplication.class, args);
	}

}
