package com.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.mysql.cj.jdbc.MysqlDataSource;

@Configuration
@PropertySource(value= {"classpath:application.properties"})
public class DbConfig {
	@Value("${spring.datasource.url}")
	private String url;
	@Value("${spring.datasource.username}")
	private String userName;
	@Value("${spring.datasource.password}")
	private String password;
	
	public DataSource create() {
		MysqlDataSource datasource=new MysqlDataSource();
		datasource.setUrl(url); //Property Injection 屬性注入
		datasource.setUser(userName);
		datasource.setPassword(password);
		return datasource;
	}
	
}
