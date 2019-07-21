package org.spring.springboot.service.impl;

import org.spring.springboot.domain.CruiseTaskEntity;
import org.spring.springboot.domain.CruiseTrackEntity;
import org.spring.springboot.domain.PresetPositionEntity;
import org.spring.springboot.service.ICruiseTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;


@Service
public class CruiseTaskServiceImpl implements ICruiseTaskService {

    public HashMap<String, CruiseTaskEntity> cruiseTaskMap = new HashMap<>();

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    private CruiseTrackServiceImpl trackService;

    /*
    * 根据设备id，用户id，巡航轨迹id，创建一个巡航任务（巡航线程）
    * */
    @Override
    public String createCruiseTask(Integer userId,Integer devId,long trackId,long startTime,long endTime) {
        /// 根据设备id，用户id，时间戳，创建任务id
        String taskId = userId + "_" + devId +"_" + Calendar.getInstance().getTimeInMillis()/1000;
        CruiseTaskEntity taskEntity = new CruiseTaskEntity();
        taskEntity.setDevId(devId);
        taskEntity.setUserId(userId);
        taskEntity.setCruiseTrackId(trackId);
        taskEntity.setStartTime(startTime);
        taskEntity.setEndTime(endTime);

        try{
            CruiseTaskThread taskThread1 = new CruiseTaskThread(taskId,taskEntity);
            taskThread1.setTaskState(2);            // 新创建的 巡航线程 处于 就绪状态
            // ThreadPoolExecutor 配置的拒绝策略为，若分配线程失败，则抛出异常，因此，若创建失败，则会运行catch分支；若成功，则线程会发送信号，解除wait的阻塞
            threadPoolExecutor.execute(taskThread1);
        }catch(Exception ex){
            ex.printStackTrace();
            taskId = null;
        }
        return taskId;
    }

    @Override
    public int deleteCruiseTask(String taskId) {

        return 0;
    }

    /*
     * 功能：  根据任务id，控制巡航任务 状态； 1 表示 启动或重启，2 表示暂停；0 表示终止
     * 返回值: status 0 表示启动巡航任务正常； -1 表示任务id位置，需要重新创建巡航任务；-2 表示任务id为空或null
     * */
    @Override
    public int ctrlCruiseTaskStats(String taskId, int taskState) {
        int status = 0 ;
        CruiseTaskEntity taskEntity = null;
        // 1. 根据任务id，获取任务对象
        if(taskId != null && !taskId.isEmpty()){

            if(cruiseTaskMap.containsKey(taskId)){
                taskEntity = cruiseTaskMap.get(taskId);
            }else{
                status = -1;
                return status;
            }
        }else{
            status = -2;
            return status;
        }

        // taskState 为 2 表示暂停巡航任务，此时，暂停 巡航线程的操作
        taskEntity.getTaskThread().setTaskState(taskState);

        return 0;
    }

    public class CruiseTaskThread implements Runnable{

        private CruiseTaskEntity cruiseTaskEntity;
        /*
        * 0 表示停止或终止线程；1 表示运行； 2 表示暂停
        * */
        private String taskId;
        public String getTaskId() {
            return taskId;
        }

        private int taskState;
        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public int getTaskState() {
            return taskState;
        }

        public void setTaskState(int taskState) {
            this.taskState = taskState;
        }

        public CruiseTaskThread(String taskId,CruiseTaskEntity cruiseTaskEntity){
            this.taskId = taskId;
            this.cruiseTaskEntity = cruiseTaskEntity;
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

            long trackId = cruiseTaskEntity.getCruiseTrackId();
            CruiseTrackEntity trackEntity = trackService.cruiseTrackMap.get(trackId);
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
            this.cruiseTaskEntity.setTaskThread(this);
            cruiseTaskMap.put(taskId,this.cruiseTaskEntity);


            for(  ;  ; ){
                if(taskState == 0)          // 终止巡航任务，退出巡航线程
                    break;
                int presetPosIdx = 0 ;              // 记录将要运动到的预置位在list中下标，不是预置位的编号
                int tsCnt = 0 ;                     // 在调用设备sdk接口后，等待设备运动到某个预置位的时间，单位为秒
                int presetNo = 1;                   // 预置位编号，用户调用设备sdk接口时，作为参数
                int cycleCnt = 1;                   // 记录当前执行的是巡航任务的第几圈儿，用户在客户端展示

                // 调用sdk接口，控制相机运转到第一个预置位
                PresetPositionEntity presetPositionEntity = list.get(0);
                tsCnt = presetPositionEntity.getRotateSeconds() + presetPositionEntity.getFocusSeconds() + presetPositionEntity.getSampleSeconds();
                presetNo = presetPositionEntity.getPresetNo();
                // deviceSDK.GotoPreset(presetNo);         // 调用设备接口，运转到第一个预置位

                cruiseTaskEntity.setCurrentPresetNo(presetNo);
                cruiseTaskEntity.setCurrentCycleNo(1);
                cruiseTaskEntity.setCurrentPresetFuncStartTs((int)(Calendar.getInstance().getTimeInMillis()/1000));
                long currentTs = Calendar.getInstance().getTimeInMillis() ;     // 当前时间

                if(taskState != 2){         // 2 表示暂停 巡航任务，此时 线程仅执行sleep和时间判断

                    // 运动到每个预置位，有固定的时间，每当时间到期，表示已经运动到指定的预置位，对焦完成，并完成了图片采集，此时可执行检测操作
                    if(-- tsCnt == 0) {
                        System.out.println(Calendar.getInstance().getTime().toString() +  " --- copy image to CurrentImage on presetNo:" +cruiseTaskEntity.getCurrentPresetNo() );
                        // 1. 向相机callback中的图片，复制一份，存放到 当前帧 对象中
                        for(int i = 0 ; i < 10 ; i ++){
                            for(int j = 0 ; j < 10000 ; j ++){

                            }
                        }

                        System.out.println(Calendar.getInstance().getTime().toString() +  " --- get next presetNo:" +cruiseTaskEntity.getCurrentPresetNo() );
                        // 3. 获取巡航轨迹上的下一个预置位对象
                        presetPosIdx ++ ;
                        if(presetPosIdx == list.size() -1) {
                            presetPosIdx = 0;
                            cruiseTaskEntity.setCurrentCycleNo(++cycleCnt);
                        }

                        presetPositionEntity = list.get(presetPosIdx);
                        tsCnt = presetPositionEntity.getRotateSeconds() + presetPositionEntity.getFocusSeconds() + presetPositionEntity.getSampleSeconds();

                        presetNo = presetPositionEntity.getPresetNo();
                        // deviceSDK.GotoPreset(presetNo);         // 调用设备接口，运转到指定预置位

                        cruiseTaskEntity.setCurrentPresetNo(presetNo);
                    }else{
                        System.out.println(Calendar.getInstance().getTime().toString() +  " --- Device is moving,cycle:"+ cruiseTaskEntity.getCurrentCycleNo()
                                + ",presetNo:" +cruiseTaskEntity.getCurrentPresetNo() );
                    }
                }else{
                    System.out.println(Calendar.getInstance().getTime().toString() +  " --- task paused, waiting to running");
                }

                try{
                    Thread.sleep(1000);
                }catch(Exception ex){
                    ex.printStackTrace();
                }

                currentTs = Calendar.getInstance().getTimeInMillis()/1000 ;
                // 若当前时间没有在巡航任务的时间范围内，则任务线程处于 "空转" 状态；
                if(currentTs < cruiseTaskEntity.getStartTime() || currentTs > cruiseTaskEntity.getEndTime()) {
                    taskState = 2;
                }
                // 若当前时间超过了 巡航任务的截止时间，则表示巡航任务结束，将状态taskState 设置为0 ，退出线程循环
                if(currentTs > cruiseTaskEntity.getEndTime()){
                    taskState = 0;
                }
            }

            System.out.println(Calendar.getInstance().getTime().toString() +  " --- cruiseTask:" + taskId + " done,thread finished");
        }
    }

}
