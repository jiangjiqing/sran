package com.hongshen.sran_service;

import com.hongshen.sran_service.common.JerseyConfig;
import com.hongshen.sran_service.controller.TaskWSController;
import javafx.application.Application;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
@EnableTransactionManagement//开启事务管理
@MapperScan("com.hongshen.sran_service.dao")//与dao层的@Mapper二选一写上即可(主要作用是扫包)
public class SranServiceMain {

	public static void main(String[] args) {
		//SpringApplication.run(SranServiceMain.class, args);
		SpringApplication springApplication = new SpringApplication(SranServiceMain.class);
		ConfigurableApplicationContext configurableApplicationContext = springApplication.run(args);
		TaskWSController.setApplicationContext(configurableApplicationContext);
	}

	@Bean
	public ServletRegistrationBean jerseyServlet() {

		System.out.println("==================================");
		System.out.println("       无线网络智能管控平台         ");
		System.out.println("==================================");
		ServletRegistrationBean registration =
				new ServletRegistrationBean(new ServletContainer(), "/*");

		registration.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, JerseyConfig.class.getName());

		return registration;
	}
//	private CorsConfiguration buildConfig() {
//		CorsConfiguration corsConfiguration = new CorsConfiguration();
//		corsConfiguration.addAllowedOrigin("*");
//		corsConfiguration.addAllowedHeader("*");
//		corsConfiguration.addAllowedMethod("*");
//		return corsConfiguration;
//	}
//
//	/**
//	 * 跨域过滤器
//	 * @return
//	 */
//	@Bean
//	public CorsFilter corsFilter() {
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", buildConfig()); // 4
//		return new CorsFilter(source);
//	}
}