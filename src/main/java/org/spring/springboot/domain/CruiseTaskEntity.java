package org.spring.springboot.domain;

import org.spring.springboot.service.impl.CruiseTaskThread;

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
    private int  currentPresetFuncNo;                      // (由于sdk中的接口可以更具预置位编号，直接运动，对焦，因此不需要记录运转到某个预置位的详情)当前所执行的操作，转动云台 1，对焦 2，采样 3，用于在客户端 实时展示巡航进度
    private int  currentPresetFuncStartTs;                 // 执行某个预置位的 转动云台，对焦，采样 的起始时间戳，用于在客户端 实时展示巡航进度
    private int  currentCycleNo;                            // 当前在执行第几圈 巡航测试；
    private String taskDetails;                             // 任务详细描述，由创建巡航任务的操作人员通过客户端填写，也可为空
    private CruiseTaskThread taskThread;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public long getDevId() {
        return devId;
    }

    public void setDevId(long devId) {
        this.devId = devId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getCruiseTrackId() {
        return cruiseTrackId;
    }

    public void setCruiseTrackId(long cruiseTrackId) {
        this.cruiseTrackId = cruiseTrackId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getCurrentPresetNo() {
        return currentPresetNo;
    }

    public void setCurrentPresetNo(int currentPresetNo) {
        this.currentPresetNo = currentPresetNo;
    }

    public int getCurrentPresetFuncNo() {
        return currentPresetFuncNo;
    }

    public void setCurrentPresetFuncNo(int currentPresetFuncNo) {
        this.currentPresetFuncNo = currentPresetFuncNo;
    }

    public int getCurrentPresetFuncStartTs() {
        return currentPresetFuncStartTs;
    }

    public void setCurrentPresetFuncStartTs(int currentPresetFuncStartTs) {
        this.currentPresetFuncStartTs = currentPresetFuncStartTs;
    }

    public int getCurrentCycleNo() {
        return currentCycleNo;
    }

    public void setCurrentCycleNo(int currentCycleNo) {
        this.currentCycleNo = currentCycleNo;
    }

    public String getTaskDetails() {
        return taskDetails;
    }

    public void setTaskDetails(String taskDetails) {
        this.taskDetails = taskDetails;
    }

    public CruiseTaskThread getTaskThread() {
        return taskThread;
    }

    public void setTaskThread(CruiseTaskThread taskThread) {
        this.taskThread = taskThread;
    }
}
