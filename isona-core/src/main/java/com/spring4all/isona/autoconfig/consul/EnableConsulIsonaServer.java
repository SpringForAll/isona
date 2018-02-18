package com.spring4all.isona.autoconfig.consul;

import com.spring4all.isona.autoconfig.CommonAutoConfig;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({CommonAutoConfig.class, ConsulIsonaAutoConfig.class})
@EnableDiscoveryClient
public @interface EnableConsulIsonaServer {
}
