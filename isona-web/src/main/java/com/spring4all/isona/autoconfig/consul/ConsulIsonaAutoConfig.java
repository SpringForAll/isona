package com.spring4all.isona.autoconfig.consul;

import com.spring4all.isona.service.DiscoveryService;
import com.spring4all.isona.service.impl.ConsulDiscoveryService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsulIsonaAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public DiscoveryService discoveryService() {
        return new ConsulDiscoveryService();
    }

}
