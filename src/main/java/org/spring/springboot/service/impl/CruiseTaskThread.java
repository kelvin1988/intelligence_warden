package org.spring.springboot.service.impl;

import org.spring.springboot.domain.CruiseTaskEntity;
import org.spring.springboot.domain.CruiseTrackEntity;
import org.spring.springboot.domain.PresetPositionEntity;

import java.util.Calendar;
import java.util.List;

public class CruiseTaskThread  implements Runnable{

    private CruiseTrackServiceImpl trackService;
    /*
     * 0 表示停止或终止线程；1 表示运行； 2 表示暂停
     * */
    private String taskId;
    private CruiseTaskServiceImpl taskService;
    private int taskState;

    public void setTaskState(int taskState) {
        this.taskState = taskState;
    }

    public CruiseTaskThread(CruiseTaskServiceImpl taskService, String taskId,CruiseTrackServiceImpl trackService){
        this.taskId = taskId;
        this.taskService = taskService;
        this.trackService = trackService;
    }

    public synchronized void waitThreadReady(long timeout){
        try {
            this.wait(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void run(){

        CruiseTaskEntity taskEntity = taskService.cruiseTaskMap.get(taskId);
        CruiseTrackEntity trackEntity = trackService.cruiseTrackMap.get(taskEntity.getCruiseTrackId());

        // 1. 参数验证
        if(trackEntity == null) {
            System.err.println(Calendar.getInstance().getTime().toString() +  " --- cruise track is null,task can't be started");
            return;
        }
        String presetPosStr = trackEntity.getPresetPositionQueue();
        List<PresetPositionEntity> list = trackService.parsePresetPositionStr2List(presetPosStr,5);
        if(list == null) {
            System.err.println(Calendar.getInstance().getTime().toString() +  " --- preset position list is null,task can't be started");
            return;
        }

        // 2. 开始安装巡航轨迹，执行巡航任务
        // 当巡航线程启动后，巡航任务才算创建成功，将新创建的巡航任务添加到 任务字典中
        System.out.println(Calendar.getInstance().getTime().toString() +  " --- begin to exeucte cruiseTask:" + taskId);
        taskState = 2;                      // 当线程创建后，将状态设置为ready
        taskEntity.setTaskThread(this);

        int presetPosIdx = 0 ;              // 记录将要运动到的预置位在list中下标，不是预置位的编号
        int presetNo = 1;                   // 预置位编号，用户调用设备sdk接口时，作为参数
        boolean isTaskStarter = true;         //  是否为巡航任务的第一个巡航点
        int tsCnt = 1 ;                     // 在调用设备sdk接口后，等待设备运动到某个预置位的时间，单位为秒
        int cycleCnt = 1;                   // 记录当前执行的是巡航任务的第几圈儿，用户在客户端展示
        PresetPositionEntity presetPositionEntity = null;
        long currentTs = Calendar.getInstance().getTimeInMillis() ;     // 当前时间
        for(  ;  ; ){
            if(taskState == 0)          // 终止巡航任务，退出巡航线程
                break;
            if(taskState == 2){         // 暂停
                try{
                    Thread.sleep(1000);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
                System.out.println(Calendar.getInstance().getTime().toString() +  " --- task paused, waiting to running");
                continue;
            }

            if(taskState == 1){         // 1 表示开始执行 巡航任务

                // 一个巡航点的完整操作包括，运动到预置位，对焦，图片采集，调用算法识别，在图片上绘制ROI，将绘制后的图片保存为 "最新帧图片"
                // 对于巡航任务的第一个巡航点，需要特殊的操作
                if(isTaskStarter){
                    presetPositionEntity = list.get(0);
                    tsCnt = presetPositionEntity.getRotateSeconds() + presetPositionEntity.getFocusSeconds() + presetPositionEntity.getSampleSeconds();
                    presetNo = presetPositionEntity.getPresetNo();

                    System.out.println(Calendar.getInstance().getTime().toString() +  " --- device sdk, goto preset");
                    // deviceSDK.GotoPreset(presetNo);         // 调用设备接口，运转到第一个预置位
                    isTaskStarter = false;

                    taskEntity.setCurrentPresetNo(presetNo);
                    taskEntity.setCurrentCycleNo(1);
                    taskEntity.setCurrentPresetFuncStartTs((int)(Calendar.getInstance().getTimeInMillis()/1000));           // 记录 执行当前预置位时的时间戳
                }

                // 运动到每个预置位，有固定的时间，每当时间到期，表示已经运动到指定的预置位，对焦完成，并完成了图片采集，此时可执行检测操作
                if(-- tsCnt == 0) {
                    System.out.println(Calendar.getInstance().getTime().toString() +  " --- copy image to CurrentImage on presetNo:" +taskEntity.getCurrentPresetNo() );
                    // 1. 向相机callback中的图片，复制一份，存放到 当前帧 对象中
                    for(int i = 0 ; i < 10 ; i ++){
                        for(int j = 0 ; j < 10000 ; j ++){

                        }
                    }
                    // 2. 调用算法识别图片，绘制ROI，并将结果 保存为 "最新帧图片"
                    System.out.println(Calendar.getInstance().getTime().toString() +  " --- save image to lastFrame" );

                    // 3. 获取巡航轨迹上的下一个预置位对象
                    presetPosIdx ++ ;
                    /// 当执行完最后一个巡航点（预置位）后，下一个执行的巡航点为巡航轨迹中的一个巡航点（预置位）
                    if(presetPosIdx == list.size() -1) {
                        presetPosIdx = 0;
                        taskEntity.setCurrentCycleNo(++cycleCnt);
                    }
                    System.out.println(Calendar.getInstance().getTime().toString() +  " --- get next presetNo:" +taskEntity.getCurrentPresetNo() );

                    presetPositionEntity = list.get(presetPosIdx);      // 根据预置位编号，获取预置位数据对象
                    // 计算 转动，对焦，图片采集所需时间
                    tsCnt = presetPositionEntity.getRotateSeconds() + presetPositionEntity.getFocusSeconds() + presetPositionEntity.getSampleSeconds();

                    presetNo = presetPositionEntity.getPresetNo();
                    // deviceSDK.GotoPreset(presetNo);         // 调用设备接口，运转到指定预置位

                    taskEntity.setCurrentPresetNo(presetNo);
                }else{
                    try{
                        Thread.sleep(1000);
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                    System.out.println(Calendar.getInstance().getTime().toString() +  " --- Device is moving,cycle:"+ taskEntity.getCurrentCycleNo()
                            + ",presetNo:" +taskEntity.getCurrentPresetNo() );
                }
            }

            currentTs = Calendar.getInstance().getTimeInMillis()/1000 ;
            // 若当前时间没有在巡航任务的时间范围内，则任务线程处于 "空转" 状态；
            if(currentTs < taskEntity.getStartTime() || currentTs > taskEntity.getEndTime()) {
                taskState = 2;
            }
            // 若当前时间超过了 巡航任务的截止时间，则表示巡航任务结束，将状态taskState 设置为0 ，退出线程循环
            if(currentTs > taskEntity.getEndTime()){
                taskState = 0;
            }
        }

        System.out.println(Calendar.getInstance().getTime().toString() +  " --- cruiseTask:" + taskId + " done,thread finished");
    }
}
