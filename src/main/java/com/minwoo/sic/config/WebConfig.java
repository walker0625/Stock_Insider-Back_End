package com.minwoo.sic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		// http 자체가 80 port를 포함하고 있기 때문에(aws 설정/nginx 설정) 따로 :80을 적어주면 오히려 cors 발생
		.allowedOrigins("http://localhost");
	}
	
}
