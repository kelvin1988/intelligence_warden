package org.spring.springboot.domain;

/*
* 设备的静态信息；其他数据对象都会通过devId关联到此对象，因此，此对象结构较为简单；
* */
public class DeviceBasicInfoEntity {

    private long devId;
    private String devName;
    private String ip;
    private int port;
    private String loginName;
    private String loginPasswd;
    private int status;             // 0 未连接； 1 已连接，待机； 2 正在执行巡航任务； 3 续航任务暂停；
}
