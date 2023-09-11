package com.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

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
	
	@Bean
	@Qualifier("sannystudio") //自訂合格名稱(適時選用)
	public DataSource create() {
		MysqlDataSource datasource=new MysqlDataSource();
		datasource.setUrl(url); //Property Injection 屬性注入
		datasource.setUser(userName);
		datasource.setPassword(password);
		return datasource;
	}
	
	@Bean
	@Qualifier("sannystudioJDBC")
	public JdbcTemplate createNorthwindJdbcTemplate(@Qualifier("sannystudio")DataSource datasource) {
		System.out.println("JdbcTemplate產生了..."+datasource);
		//建構一個個體物件
		JdbcTemplate jdbc=new JdbcTemplate();
		//與DataSource 連接工廠物件互動
		jdbc.setDataSource(datasource);
		return jdbc;
		
	}
	
}
