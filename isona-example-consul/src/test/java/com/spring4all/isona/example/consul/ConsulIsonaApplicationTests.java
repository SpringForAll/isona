package com.spring4all.isona.example.consul;

import com.spring4all.isona.service.DiscoveryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConsulIsonaApplication.class)
public class ConsulIsonaApplicationTests {

	@Autowired
	private DiscoveryService discoveryService;

	@Test
	public void getAllServices() {
		discoveryService.getServices().forEach(service -> log.info(service));
	}

	@Test
	public void getAllServiceDetails() {
		discoveryService.getServices().forEach(service -> {
			discoveryService.getServiceInstances(service).forEach(instance -> {
				log.info("");
			});
		});
	}

}
