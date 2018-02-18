package com.spring4all.isona.service.impl;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.health.model.Check;
import com.ecwid.consul.v1.health.model.HealthService;
import com.spring4all.isona.storage.domain.IsonaInstance;
import com.spring4all.isona.storage.domain.IsonaInstanceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ConsulDiscoveryService extends AbstractDiscoveryService {

    @Autowired
    private ConsulDiscoveryClient consulDiscoveryClient;

    @Autowired
    private ConsulClient consulClient;

    @Override
    public List<IsonaInstance> getServiceInstances(String serviceName) {
        // 处理下consul返回的服务信息
        List<IsonaInstance> result = new ArrayList<>();
        List<HealthService> response = consulClient.getHealthServices(serviceName, false, null).getValue();
        for(HealthService service : response) {
            if(service.getService().getService().equals("consul")) {
                continue;
            }

            IsonaInstance instanceInfo = new IsonaInstance();

            // 基础信息
            instanceInfo.setServiceName(serviceName);
            instanceInfo.setServiceAddress(service.getService().getAddress());
            instanceInfo.setServicePort(service.getService().getPort());

            instanceInfo.setNode(service.getNode().getNode());
            instanceInfo.setAddress(service.getNode().getAddress());
            instanceInfo.setMetadata(service.getService().getTags().toString());

            // 健康状态信息
            for (Check check : service.getChecks()) {
                switch (check.getStatus()) {
                    case PASSING: instanceInfo.setStatus(IsonaInstanceStatus.UP);break;
                    case UNKNOWN: instanceInfo.setStatus(IsonaInstanceStatus.UNKNOWN);break;
                    case WARNING: instanceInfo.setStatus(IsonaInstanceStatus.WARNING);break;
                    case CRITICAL: instanceInfo.setStatus(IsonaInstanceStatus.DOWN);break;
                    default: instanceInfo.setStatus(IsonaInstanceStatus.UNKNOWN); break;
                }
            }

            // 填充其他信息
            fillInstanceMetrics(instanceInfo);

            result.add(instanceInfo);
        }

        return result;
    }

    @Override
    public List<IsonaInstance> getAllServiceInstances() {
        List<IsonaInstance> result = new Vector<>();
        List<String> serviceList = getServices();

        // FIXME 当服务实例多了，会有潜在问题，待后续完善
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 20,
                500L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(200));
        CountDownLatch c = new CountDownLatch(serviceList.size());

        for (String service : serviceList) {
            executor.execute(()->{
                result.addAll(getServiceInstances(service));
                c.countDown();
            });
        }

        try {
            c.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("Total Service : " + serviceList.size() + ", Total Instances : " + result.size());
        return result;
    }

}
