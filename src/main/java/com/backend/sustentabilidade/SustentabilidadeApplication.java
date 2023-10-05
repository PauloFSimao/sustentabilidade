package com.backend.sustentabilidade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

//@EntityScan(basePackages = "com.backend.sustentabilidade.model")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, SecurityAutoConfiguration.class})
@EnableFeignClients
public class SustentabilidadeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SustentabilidadeApplication.class, args);
	}

}
