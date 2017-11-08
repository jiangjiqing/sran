package com.hongshen.sran_service;

import com.hongshen.sran_service.common.JerseyConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement//开启事务管理
@MapperScan("com.hongshen.sran_service.dao")//与dao层的@Mapper二选一写上即可(主要作用是扫包)
public class SranServiceMain {

	public static void main(String[] args) {
		SpringApplication.run(SranServiceMain.class, args);
	}

	@Bean
	public ServletRegistrationBean jerseyServlet() {

		ServletRegistrationBean registration =
				new ServletRegistrationBean(new ServletContainer(), "/*");

		registration.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, JerseyConfig.class.getName());

		return registration;

	}
}