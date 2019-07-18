package org.spring.springboot.domain;

/*
* 此数据对象 用于记录巡航轨迹的静态数据；与 CruiseTaskEntity 需要区分开，CruiseTaskEntity 用于记录 巡航任务 的动态状态；
* */
public class CruiseTrackEntity {

    //
    private Long trackId;
    private String trackName;
    /*
    * 1,15,10,10;2,15,10,10;3,15,10,10;4,15,10,10;5,15,10,10;6,15,10,10;7,15,10,10;8,15,10,10;
    * 使用 ';'分割 各个预置位信息；每个预置位由 唯一编号(取值范围0~255), 云台转动时间，镜头聚焦时间, 数据采集时间；四个字段组成；各个字段之间使用','分割
    */
    private String presetPositionQueue;
    private Long devId;
    private Integer funcNo;
    private Integer cruisePolicy;
    private Long updateTime;
    private Long createTime;

    public Long getTrackId() {
        return trackId;
    }

    public void setTrackId(Long trackId) {
        this.trackId = trackId;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getPresetPositionQueue() {
        return presetPositionQueue;
    }

    public void setPresetPositionQueue(String presetPositionQueue) {
        this.presetPositionQueue = presetPositionQueue;
    }

    public Long getDevId() {
        return devId;
    }

    public void setDevId(Long devId) {
        this.devId = devId;
    }

    public Integer getFuncNo() {
        return funcNo;
    }

    public void setFuncNo(Integer funcNo) {
        this.funcNo = funcNo;
    }

    public Integer getCruisePolicy() {
        return cruisePolicy;
    }

    public void setCruisePolicy(Integer cruisePolicy) {
        this.cruisePolicy = cruisePolicy;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
