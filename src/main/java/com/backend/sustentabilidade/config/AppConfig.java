package com.backend.sustentabilidade.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mysql.cj.jdbc.MysqlDataSource;

@Configuration
public class AppConfig implements WebMvcConfigurer{
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
	}
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource driver = new DriverManagerDataSource();
		driver.setUrl("jdbc:mysql://localhost/sustentabilidade?useTimezone=true&serverTimezone=UTC");
		driver.setUsername("root");
		driver.setPassword("root");
		driver.setDriverClassName("com.mysql.cj.jdbc.Driver");
		
		return driver;
	}
	
	
	
	@Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.MYSQL);// TIPO DE DATABASE
        adapter.setDatabasePlatform("org.hibernate.dialect.MySQL8Dialect");// ESPECIFICAR O DIALETO QUE SERÁ USADO PARA CONVERSAR COM A DATABASE, MySQL8Dialect LINGUAGEM QUE VAI SER USADA
        adapter.setPrepareConnection(true);// PREPARAR A CONEXÃO PARA MANDAR INSTRUÇÕES
        adapter.setGenerateDdl(true);// GERAR O DDL, SERIA FALSO SE A TABELA  JÁ EXISTISSE
        adapter.setShowSql(true);// TODAS AS APLICAÇÕES SQL APARECERA NO CONSOLE, PARA CONTROLAR O QUE ESTÁ ACONTECENDO, UM INFORMATIVO
        
        return adapter;
    }
}
