package com.spring4all.isona.autoconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("isona")
public class IsonaProperties {

    private ServiceProperties service = new ServiceProperties();

    public String getSerivceManagementContextPath() {
        return this.service.getManagementContextPath();
    }

    public String getSerivceInfoUrl() {
        return this.service.getInfoUrl();
    }

    public String getSerivceMetricsUrl() {
        return this.service.getMetricsUrl();
    }

    @Data
    static class ServiceProperties {

        /**
         * 监控端点的context-path，对应微服务的management.context-path配置内容
         **/
        private String managementContextPath = "";

        private String infoUrl = "/info";
        private String metricsUrl = "/metrics";

    }
}


