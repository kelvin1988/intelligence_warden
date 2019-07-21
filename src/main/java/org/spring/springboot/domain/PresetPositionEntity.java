package org.spring.springboot.domain;

/*
* 记录 预置位的信息；定义 巡航轨迹时，根据预置位编号，来指定巡航所经过的各个预置位；转动到指定的预置位，在预置位指定对焦所花时间，采样所用时间，
* 由 巡航轨迹来定义，对于不同的巡航轨迹，在同一个预置位所 转动，对焦，采样的时间可能会不同；这些时间由巡航轨迹对象来定义，可以增加灵活性
* */
public class PresetPositionEntity {

    private int presetNo;           // 预置位编号,不同设备的预置位编号会重复，但同一个设备的预置位编号唯一
    private String name;            // 预置位的名称
    private long devId;             // 预置位所属设备id
    private float rotatePos;           // 运动到此预置位，需要旋转的角度（相机可以直接根据预置位编号运动到指定的位置，此字段可能用不上）
    private int rotateSeconds;       // 运动到此预置位，云台运动时间，单位为秒
    private int tiltPos;                // 运动到此预置位，需要俯仰的角度（相机可以直接根据预置位编号运动到指定的位置，此字段可能用不上）
    private int focusPos;          // 此预置位，镜头对角的位置（相机可以直接根据预置位编号运动到指定的位置，此字段可能用不上）
    private int focusSeconds;            // 运动到此预置位后，对焦需要的时间，单位为秒
    private int sampleSeconds;          // 运动到此预置位后，相机拍摄采样所需时间，单位为秒
    private long timestamp;        // 添加或更新此预置位的时间戳

    public int getPresetNo() {
        return presetNo;
    }

    public void setPresetNo(int presetNo) {
        this.presetNo = presetNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDevId() {
        return devId;
    }

    public void setDevId(long devId) {
        this.devId = devId;
    }

    public float getRotatePos() {
        return rotatePos;
    }

    public void setRotatePos(float rotatePos) {
        this.rotatePos = rotatePos;
    }

    public int getRotateSeconds() {
        return rotateSeconds;
    }

    public void setRotateSeconds(int rotateSeconds) {
        this.rotateSeconds = rotateSeconds;
    }

    public int getTiltPos() {
        return tiltPos;
    }

    public void setTiltPos(int tiltPos) {
        this.tiltPos = tiltPos;
    }

    public int getFocusPos() {
        return focusPos;
    }

    public void setFocusPos(int focusPos) {
        this.focusPos = focusPos;
    }

    public int getFocusSeconds() {
        return focusSeconds;
    }

    public void setFocusSeconds(int focusSeconds) {
        this.focusSeconds = focusSeconds;
    }

    public int getSampleSeconds() {
        return sampleSeconds;
    }

    public void setSampleSeconds(int sampleSeconds) {
        this.sampleSeconds = sampleSeconds;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
