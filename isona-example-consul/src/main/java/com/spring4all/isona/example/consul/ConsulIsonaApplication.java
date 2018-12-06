package com.spring4all.isona.example.consul;

import com.spring4all.isona.autoconfig.consul.EnableConsulIsonaServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableConsulIsonaServer
@SpringBootApplication
public class ConsulIsonaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsulIsonaApplication.class, args);
	}

}
