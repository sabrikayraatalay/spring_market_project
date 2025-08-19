package com.KayraAtalay.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"com.KayraAtalay" })
@EntityScan(basePackages = {"com.KayraAtalay" })
@EnableJpaRepositories(basePackages = {"com.KayraAtalay"})
@EnableScheduling
public class MarketApplicationStarter {

	public static void main(String[] args) {
		SpringApplication.run(MarketApplicationStarter.class, args);
	}

}
