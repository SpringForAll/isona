package com.spring4all.isona.autoconfig.eureka;

import com.spring4all.isona.service.DiscoveryService;
import com.spring4all.isona.service.impl.EurekaDiscoveryService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
public class EurekaIsonaAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public DiscoveryService discoveryService() {
        return new EurekaDiscoveryService();
    }

}
