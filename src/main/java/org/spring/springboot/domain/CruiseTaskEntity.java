package org.spring.springboot.domain;

/*
* 巡航任务数据对象，用于记录巡航任务的执行状态；与记录巡航轨迹静态数据的 CruiseTrackEntity不同；
* 每当需要执行巡航任务时，创建一个对象，记录巡航任务执行过程中的完整状态和中间数据
* */
public class CruiseTaskEntity {

    /*
    * 一个按照一定规则编码的字符串，比如 20190716_dev300000001_nornaltemptest; 某天 使用某台设备，执行常规温度测试；编码一些任务摘要，便于阅读
    */
    private String  taskId;
    private long devId;                                     // 执行巡航任务的设备id
    private long userId;                                    // 创建巡航任务的用户id
    private long cruiseTrackId;                             // 任务所执行的巡航轨迹的id
    private long startTime;                                 // 任务的起始时间时间戳，单位为秒
    private long endTime;                                   // 任务的结束时间时间戳，单位为秒
    private int  currentPresetNo;                          // 当前所在的预置位编号
    private int  currentPresetFuncNo;                      // 当前所执行的操作，转动云台 1，对焦 2，采样 3，用于在客户端 实时展示巡航进度
    private int  currentPresetFuncStartTs;                 // 执行某个预置位的 转动云台，对焦，采样 的起始时间戳，用于在客户端 实时展示巡航进度
    private int  currentCycleNo;                            // 当前在执行第几圈 巡航测试；
    private String taskDetails;                             // 任务详细描述，由创建巡航任务的操作人员通过客户端填写，也可为空
}
