package org.spring.springboot.domain;

public class CruiseTrackEntity {

    private Long trackId;
    private String trackName;
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
