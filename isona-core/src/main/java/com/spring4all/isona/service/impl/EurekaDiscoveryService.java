package com.spring4all.isona.service.impl;

import com.netflix.appinfo.InstanceInfo;
import com.spring4all.isona.storage.domain.IsonaInstance;
import com.spring4all.isona.storage.domain.IsonaInstanceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class EurekaDiscoveryService extends AbstractDiscoveryService {

    @Autowired
    private EurekaDiscoveryClient eurekaDiscoveryClient;

    @Override
    public List<IsonaInstance> getServiceInstances(String serviceName) {
        // TODO 处理下eureka返回的服务信息

        List<IsonaInstance> result = new ArrayList<>();

        for(ServiceInstance instance : eurekaDiscoveryClient.getInstances(serviceName)) {
            InstanceInfo eurekaInstance = ((EurekaDiscoveryClient.EurekaServiceInstance) instance).getInstanceInfo();

            IsonaInstance instanceInfo = new IsonaInstance();

            // 基础信息
            instanceInfo.setServiceName(serviceName);
            instanceInfo.setServiceAddress(eurekaInstance.getIPAddr());
            instanceInfo.setServicePort(eurekaInstance.getPort());

            instanceInfo.setNode(eurekaInstance.getIPAddr());
            instanceInfo.setAddress(eurekaInstance.getIPAddr());
            instanceInfo.setMetadata(eurekaInstance.getMetadata().toString());

            // 健康状态信息
            switch (eurekaInstance.getStatus()) {
                case UP: instanceInfo.setStatus(IsonaInstanceStatus.UP);break;
                case UNKNOWN: instanceInfo.setStatus(IsonaInstanceStatus.UNKNOWN);break;
                case STARTING: instanceInfo.setStatus(IsonaInstanceStatus.WARNING);break;
                case OUT_OF_SERVICE: instanceInfo.setStatus(IsonaInstanceStatus.WARNING);break;
                case DOWN: instanceInfo.setStatus(IsonaInstanceStatus.DOWN);break;
                default: instanceInfo.setStatus(IsonaInstanceStatus.UNKNOWN); break;
            }

            // 填充其他信息
            fillInstanceMetrics(instanceInfo);

            result.add(instanceInfo);
        }

        return result;
    }

    @Override
    public List<IsonaInstance> getAllServiceInstances() {
        List<IsonaInstance> result = new ArrayList<>();
        for (String service : getServices()) {
            result.addAll(getServiceInstances(service));
        }
        return result;
    }

}
