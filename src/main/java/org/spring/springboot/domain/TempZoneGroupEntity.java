package org.spring.springboot.domain;

/*
 * 同一个设备的同一个预置位，可以将多个测温区域 编组为一个测温区域组；同一个预置位，某个测温区域可以属于一个或多个测温区域组
 */
public class TempZoneGroupEntity {

    private String name;
    private String tempZoneIdQueue;                   // 测温区域组中所有 测温区域id，使用 , 分割； 比如：1,3,5,7,8,10,234; 某个预置位最多255个测温区域
    private long devId;                               // 测温区域组所属设备；
}
