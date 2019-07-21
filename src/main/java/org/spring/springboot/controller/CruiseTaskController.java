package org.spring.springboot.controller;

import org.spring.springboot.domain.CruiseTaskEntity;
import org.spring.springboot.domain.ResponseResultEntity;
import org.spring.springboot.service.impl.CruiseTaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RequestMapping("/cruise_task")
@RestController
public class CruiseTaskController {

    @Autowired
    private CruiseTaskServiceImpl cruiseTaskService;

    @PostMapping(value={"/add_new_cruise_task"})
    @ResponseBody
    public ResponseResultEntity createNewTask(@RequestBody CruiseTaskEntity taskEntity){
        ResponseResultEntity response = new ResponseResultEntity();
        Integer userId = new Integer((int)taskEntity.getUserId());
        Integer devId = new Integer((int)taskEntity.getDevId());
        long trackId = taskEntity.getCruiseTrackId();
        long startTime = taskEntity.getStartTime();
        long endTime = taskEntity.getEndTime();

        String taskId = cruiseTaskService.createCruiseTask(userId,devId,trackId,startTime,endTime);

        if(taskId != null && !taskId.isEmpty()){
            response.setStatus(0);
            response.setMsg("task create success");
            HashMap<String,Object> data = new HashMap<>();
            data.put("taskId",taskId);
            response.setData(data);
        }else{
            response.setStatus(-1);
            response.setMsg("task create failed,taskid is null or empty");
            HashMap<String,Object> data = new HashMap<>();
        }

        return response;
    }

    @PostMapping(value={"/ctrl_cruise_task_state"})
    @ResponseBody
    public ResponseResultEntity ctrlTaskState(@RequestBody HashMap<String,Object> requestMap){
        ResponseResultEntity response = new ResponseResultEntity();
        String taskId = (String)requestMap.get("taskId");
        int taskState = Integer.parseInt(requestMap.get("taskState").toString());

        cruiseTaskService.ctrlCruiseTaskStats(taskId,taskState);
        return response;
    }
}
