package org.spring.springboot.service;

import org.spring.springboot.domain.CruiseTaskEntity;
import org.spring.springboot.domain.CruiseTrackEntity;

import java.util.HashMap;

public interface ICruiseTaskService {

    public String createCruiseTask(Integer userId,Integer devId,long trackId,long startTime,long endTime);

//    public int startCruiseTask(String taskId);
//
//    public int stopCruiseTask(String taskId);
//
//    public int restartCruiseTask(String taskId);

    public int deleteCruiseTask(String taskId);

    public int ctrlCruiseTaskStats(String taskId,int taskState);
}
