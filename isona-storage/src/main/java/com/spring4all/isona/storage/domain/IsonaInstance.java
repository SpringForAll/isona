package com.spring4all.isona.storage.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class IsonaInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 服务名
     **/
    private String serviceName;
    /**
     * 服务ip
     **/
    private String serviceAddress;
    /**
     * 服务端口
     **/
    private Integer servicePort;
    /**
     * 服务所在节点名
     **/
    private String node;
    /**
     * 服务所在节点ip地址
     **/
    private String address;
    /**
     * 当前服务状态
     **/
    private IsonaInstanceStatus status;
    /**
     * 元数据，用key=value的方式记录，多个用英文逗号分割
     **/
    private String metadata;

    /**
     * git提交id
     **/
    private String commitId;
    /**
     * git分支
     **/
    private String branch;
    /**
     * git提交时间
     **/
    private Long commitTime;
    /**
     * 应用构建时间
     **/
    private Long buildTime;
    /**
     * 版本信息
     **/
    private String version;

    /**
     * 内存分配总数
     **/
    private Integer memTotal = 0;
    /**
     * 已经使用内存
     **/
    private Integer memUsed = 0;
    /**
     * 使用内存百分比
     **/
    private Integer memPercent = 0;

    /**
     * 当前线程数
     **/
    private Integer threads = 0;
    /**
     * 当前守护线程
     **/
    private Integer threadsDaemon = 0;
    /**
     * 历史线程峰值
     **/
    private Integer threadsPeak = 0;

    /**
     * 最近更新时间
     **/
    private Date updateTime;


}


