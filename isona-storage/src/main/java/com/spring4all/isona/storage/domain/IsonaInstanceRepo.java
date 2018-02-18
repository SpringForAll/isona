package com.spring4all.isona.storage.domain;


import org.springframework.data.repository.CrudRepository;

public interface IsonaInstanceRepo  extends CrudRepository<IsonaInstance, Long> {

    IsonaInstance findByServiceNameAndServiceAddressAndServicePort(String serviceName, String serviceAddress, Integer servicePort);

    Integer deleteByStatusEquals(String status);

}
