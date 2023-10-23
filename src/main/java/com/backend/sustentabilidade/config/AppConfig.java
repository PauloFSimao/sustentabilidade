package com.backend.sustentabilidade.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
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
	
	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		
		sender.setHost("smtp.gmail.com");
		sender.setPort(587);
		sender.setUsername("fs1898117@gmail.com");
		sender.setPassword("yyct xplt bprs nydz");
		
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.smtp.connectiontimeout", 10000);
		
		sender.setJavaMailProperties(props);
		
		return sender;
		
	}
}
