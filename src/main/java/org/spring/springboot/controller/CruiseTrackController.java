package org.spring.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.springboot.domain.ResponseResultEntity;
import org.spring.springboot.domain.CruiseTrackEntity;
import org.spring.springboot.domain.CruiseTrackPolicyEnum;
import org.spring.springboot.service.impl.CruiseTrackServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@RequestMapping("/cruise_track")
@RestController
public class CruiseTrackController {

    private static final Logger log = LoggerFactory.getLogger(CruiseTrackController.class);
    @Autowired
    private CruiseTrackServiceImpl cruiseTrackService ;

    /*
    * 新增巡航轨迹
    * */
    @PostMapping(value={"/add_new_cruise_track"})
    @ResponseBody
    public ResponseResultEntity addNewCruiseTrack(@RequestBody CruiseTrackEntity cruiseTrack){

        ResponseResultEntity response = new ResponseResultEntity();

        /// 判断 上一个 轨迹id是否为0,（程序启动时从数据库加载，正常加载后，maxFuncNo为有效值，若加载失败，则在使用时，再次尝试从数据库加载）
        if(cruiseTrackService.maxFuncNo == 0){
            try{
                cruiseTrackService.maxFuncNo = cruiseTrackService.getMaxFuncNo();
            }catch (Exception ex){
                log.debug(ex.getMessage());
                response.setStatus(-1);
                response.setMsg("get max funcNo from db failed;");
                HashMap<String,Object> dataMap = new HashMap<>();
                dataMap.put("err",ex.getMessage());
                response.setData(dataMap);

                return response;
            }
        }
        int maxFuncNo = ++ cruiseTrackService.maxFuncNo ;
        System.out.println(Calendar.getInstance().getTime().toString() +  " maxFuncNo:" + maxFuncNo);

        /// 判断 上一个 轨迹id是否为0,（程序启动时从数据库加载，正常加载后，maxTrackId为有效值，若加载失败，则在使用时，再次尝试从数据库加载）
        if(cruiseTrackService.maxTrackId == 0){
            try{
                cruiseTrackService.maxTrackId = cruiseTrackService.getTrackId();
            }catch (Exception ex){
                log.debug(ex.getMessage());
                response.setStatus(-1);
                response.setMsg("get max trackId from db failed;");
                HashMap<String,Object> dataMap = new HashMap<>();
                dataMap.put("err",ex.getMessage());
                response.setData(dataMap);

                return response;
            }
        }
        long trackId = ++ cruiseTrackService.maxTrackId  ;
        System.out.println(Calendar.getInstance().getTime().toString() +  " maxTrackId:" + trackId);
        int ret = 0;
        try{
            ret = cruiseTrackService.addNewCruiseTrack(trackId,cruiseTrack.getTrackName(),cruiseTrack.getDevId(),maxFuncNo,cruiseTrack.getPresetPositionQueue(), CruiseTrackPolicyEnum.CT_POLICY_1);
            if(ret == 1){
                response.setStatus(0);
                response.setMsg("add new cruise track success;");
                HashMap<String,Object> dataMap = new HashMap<>();
                dataMap.put("msg","dao return " + ret);
                response.setData(dataMap);
            }
        }catch(Exception ex){
            log.debug(ex.getMessage());

            response.setStatus(-1);
            response.setMsg("add new cruise track faild;" );
            HashMap<String,Object> dataMap = new HashMap<>();
            dataMap.put("err",ex.getMessage());
            response.setData(dataMap);

            return response;
        }

        return response ;
    }

    /*
    * 根据 设备id 返回某个设备可执行的所有巡航轨迹
    * */
    @PostMapping(value={"/find_all_cruise_track_by_dev_id"})
    @ResponseBody
    public ResponseResultEntity findAllCruiseTrackByDevId(@RequestBody HashMap<String,Object> requestMap){

        ResponseResultEntity response = new ResponseResultEntity();

        List<CruiseTrackEntity> list = null;
        try{
            long devId = (long)requestMap.get("devId");
            list = cruiseTrackService.findAllCruiseTrackByDevId(devId);
        }catch(Exception ex){
            response.setStatus(-1);
            response.setMsg("query failed;");
        }

        response.setStatus(0);
        response.setMsg("query success;");
        HashMap<String,Object> dataMap = new HashMap<>();
        dataMap.put("list",list);
        response.setData(dataMap);

        return  response;
    }

    /*
    * 根据指定的巡航轨迹id，执行巡航任务；userId用于记录巡航结果和巡航日志
    * 请求参数：
    * {
	* "userId":10000001,
	* "trackId":"2000000004",
	* "operate":1             // 0 终止当前巡航任务 并复位； 1 开始或继续执行当前续航任务； 2 暂停
    * }
    * 返回值：
    * {
    * "taskId":             // 巡航任务唯一编号，由trackId,设备id，执行任务的起始时间戳来编码，可以保证巡航任务的唯一性；
    * "taskStatus": 0       // 0 表示指令接收正常，非0 表示指令接收异常
    * }
    * */
    @PostMapping(value={"/execute_cruise"})
    @ResponseBody
    public ResponseResultEntity executeCruiseTrackTask(@RequestBody HashMap<String,Object> requestMap){
        ResponseResultEntity response = new ResponseResultEntity();



        return response;
    }

    /*
     * 根据指定的巡航任务id，查询任务执行状态
     * 请求参数：
     * {
     * "taskId":10000001
     * }
     * 返回值：
     * {
     * "taskId":             // 巡航任务唯一编号，由trackId,设备id，执行任务的起始时间戳来编码，可以保证巡航任务的唯一性；
     * "taskStatus": 0       // 0 表示指令接收正常，非0 表示指令接收异常
     * "timestamp":          // 状态所对应的的时间戳
     * }
     * */
    @PostMapping(value={"/query_cruise_state"})
    @ResponseBody
    public ResponseResultEntity queryCruiseTaskState(@RequestBody HashMap<String,Object> requestMap) {
        ResponseResultEntity response = new ResponseResultEntity();


        return response;
    }

    /*
    * 根据参数，编辑巡航轨迹的某个参数
    * */
    @PostMapping(value={"/edit_cruise_track"})
    @ResponseBody
    public ResponseResultEntity editCruiseTrack(@RequestBody HashMap<String,Object> requestMap) {
        ResponseResultEntity response = new ResponseResultEntity();

        return response;
    }

    /*
     * 根据巡航轨迹id，删除某个巡航轨迹
     * */
    @PostMapping(value={"/delete_cruise_track"})
    @ResponseBody
    public ResponseResultEntity deleteCruiseTrack(@RequestBody HashMap<String,Object> requestMap) {
        ResponseResultEntity response = new ResponseResultEntity();

        return response;
    }
}
