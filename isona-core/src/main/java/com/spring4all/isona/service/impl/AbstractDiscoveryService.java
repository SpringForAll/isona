package com.spring4all.isona.service.impl;

import com.spring4all.isona.autoconfig.IsonaProperties;
import com.spring4all.isona.service.DiscoveryService;
import com.spring4all.isona.utils.HttpUtils;
import com.spring4all.isona.storage.domain.IsonaInstance;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.List;
import java.util.Map;

@Slf4j
public abstract class AbstractDiscoveryService implements DiscoveryService {

    @Autowired
    protected DiscoveryClient discoveryClient;
    @Autowired
    protected IsonaProperties isonaProperties;

    @Override
    public List<String> getServices() {
        return discoveryClient.getServices();
    }

    @Override
    public int getTotalInstances() {
        int sum = 0;
        for (String service : getServices()) {
            sum += discoveryClient.getInstances(service).size();
        }
        return sum;
    }

    @Override
    public void fillInstanceInfo(IsonaInstance instanceInfo) {

        // 访问/info收集信息
        StringBuffer url = new StringBuffer()
                .append("http://")
                .append(instanceInfo.getServiceAddress())
                .append(":")
                .append(instanceInfo.getServicePort())
                .append(isonaProperties.getSerivceManagementContextPath())
                .append(isonaProperties.getSerivceInfoUrl());

        try {
            Info info = (Info) HttpUtils.call(url.toString(), Info.class);
            if (info != null) {
                log.debug(info.toString());
                if (info.getGit() != null) {
                    instanceInfo.setBranch(info.getGit().getBranch());
                    instanceInfo.setCommitId(info.getGit().getCommit().getId());
                    instanceInfo.setCommitTime(info.getGit().getCommit().getTime());
                }
                if (info.getBuild() != null) {
                    instanceInfo.setBuildTime(info.getBuild().getTime());
                }
            }
        } catch (Exception e) {
            log.warn(instanceInfo.getServiceName() + ", " + url + " : " + e.getMessage());
        }
    }

    @Override
    public void fillInstanceMetrics(IsonaInstance instanceInfo) {
        // 访问/metrics收集信息
        StringBuffer url = new StringBuffer()
                .append("http://")
                .append(instanceInfo.getServiceAddress())
                .append(":")
                .append(instanceInfo.getServicePort())
                .append(isonaProperties.getSerivceManagementContextPath())
                .append(isonaProperties.getSerivceMetricsUrl());

        try {
            Map<String, Object> metrics = (Map<String, Object>) HttpUtils.call(url.toString(), Map.class);
            if (metrics != null) {
                log.debug(metrics.toString());

                if(metrics.get("mem") != null) {
                    int mem = (Integer) metrics.get("mem");
                    int memFree = (Integer) metrics.get("mem.free");
                    instanceInfo.setMemTotal(mem);
                    instanceInfo.setMemUsed(mem - memFree);
                    double p = (double) (mem - memFree) / mem * 100;
                    instanceInfo.setMemPercent((int) p);

                    int threads = (Integer) metrics.get("threads");
                    int threadsDaemon = (Integer) metrics.get("threads.daemon");
                    int threadsPeak = (Integer) metrics.get("threads.peak");
                    instanceInfo.setThreads(threads);
                    instanceInfo.setThreadsDaemon(threadsDaemon);
                    instanceInfo.setThreadsPeak(threadsPeak);
                }
            }
        } catch (Exception e) {
            log.warn(instanceInfo.getServiceName() + ", " + url + " : " + e.getMessage());
        }
    }

    @Data
    static class Info {
        private String env;
        private GitInfo git;
        private BuildInfo build;

        @Data
        static class GitInfo {
            private Commit commit;
            private String branch;

            @Data
            static class Commit {
                private Long time;
                private String id;
            }
        }


        @Data
        static class BuildInfo {
            private String version;
            private String artifact;
            private String name;
            private String group;
            private Long time;
        }

    }

}
