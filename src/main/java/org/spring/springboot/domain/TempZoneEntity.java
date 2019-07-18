package org.spring.springboot.domain;

/*
* 在某个预置位，有多个矩形区域，这些矩形区域 通过 工作人员手工绘制，或算法识别得到，为相机测量区域；
* 同一个设备的同一个预置位，可以将多个测温区域 编组为一个测温区域组；同一个预置位，某个测温区域可以属于一个或多个测温区域组
*/
public class TempZoneEntity {

    private int tempZoneId;                          // 测温区域的唯一id
    private String tempZoneName;                     // 测温区域的名称
    private int tempZoneX;                           // 测温区域矩形区域的左上角X坐标
    private int tempZoneY;                           // 测温区域矩形区域的左上角Y坐标
    private int tempZoneWidth;                       // 测温区域矩形区域的width
    private int tempZoneHeight;                       // 测温区域矩形区域的Height
    private int presetNo;                            // 测温区域所属预置位编号
    private long devId;                              // 测温区域所属设备

}
