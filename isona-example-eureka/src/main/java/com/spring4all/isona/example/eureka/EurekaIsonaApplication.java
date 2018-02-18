package com.spring4all.isona.example.eureka;

import com.spring4all.isona.autoconfig.eureka.EnableEurekaIsonaServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableEurekaIsonaServer
@SpringBootApplication
public class EurekaIsonaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaIsonaApplication.class, args);
	}

}
