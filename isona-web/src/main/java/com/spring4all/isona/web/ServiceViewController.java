package com.spring4all.isona.web;


import com.spring4all.isona.service.DiscoveryService;
import com.spring4all.isona.storage.domain.IsonaInstance;
import com.spring4all.isona.storage.domain.IsonaInstanceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ServiceViewController {

    @Autowired
    private DiscoveryService discoveryService;

    @GetMapping("/serviceList")
    public String services(ModelMap map) {
        List<IsonaInstance> serviceList = discoveryService.getAllServiceInstances();
        map.addAttribute("serviceList", serviceList);

        int instancesNum = serviceList.size();
        int servicesNum = discoveryService.getServices().size();
        int notUpInstancesNum = 0;
        int memDangerInstancesNum = 0;
        for(IsonaInstance instance : serviceList) {
            if(instance.getStatus() != IsonaInstanceStatus.UP) {
                notUpInstancesNum ++;
            }
            if(instance.getMemPercent() >= 80) {
                memDangerInstancesNum ++;
            }
        }

        map.addAttribute("instancesNum", instancesNum);
        map.addAttribute("servicesNum", servicesNum);
        map.addAttribute("notUpInstancesNum", notUpInstancesNum);
        map.addAttribute("memDangerInstancesNum", memDangerInstancesNum);

        return "service/service_list";
    }

}
