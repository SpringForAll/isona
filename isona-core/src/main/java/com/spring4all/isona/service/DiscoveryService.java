package com.spring4all.isona.service;



import com.spring4all.isona.storage.domain.IsonaInstance;

import java.util.List;

public interface DiscoveryService {

    List<String> getServices();

    List<IsonaInstance> getServiceInstances(String serviceName);

    List<IsonaInstance> getAllServiceInstances();

    int getTotalInstances();

    void fillInstanceInfo(IsonaInstance instanceInfo);

    void fillInstanceMetrics(IsonaInstance instanceInfo);

}
