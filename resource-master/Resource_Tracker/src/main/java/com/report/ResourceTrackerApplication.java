package com.report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.report.repository")
@EntityScan(basePackages = "com.report.entity")
public class ResourceTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResourceTrackerApplication.class, args);
	}


//	(scanBasePackages = {"repository","controller","service","entity","dto","config","exceptionHandler","mapper"})
}
