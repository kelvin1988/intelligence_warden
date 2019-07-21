package org.spring.springboot.config;

import org.spring.springboot.dao.CruiseTrackDao;
import org.spring.springboot.domain.CruiseTrackEntity;
import org.spring.springboot.service.impl.CruiseTrackServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/*
* 当springboot的框架组件加载完后，进入消息循环之前，执行如下方法，完成 从数据库加载数据到内存的操作
* */
@Component
@Order(1)
public class LoadDB2CacheRunner implements ApplicationRunner {

    private final Integer ONCE_LOAD_RECORD_COUNT = 1000;

    @Autowired
    private CruiseTrackServiceImpl cruiseTrackService;

    @Autowired
    private CruiseTrackDao cruiseTrackDao;
    @Override
    public void run(ApplicationArguments args) throws Exception {

        Long totalCnt = cruiseTrackDao.getTotalRecordsOfCruiseTrack();
        int loadTimes = (int)(totalCnt / ONCE_LOAD_RECORD_COUNT);
        int lastCnt = (int)(totalCnt % ONCE_LOAD_RECORD_COUNT);
        /// 若总行数除以 每次加载的行数，有余数，则再多查询一次；
        if(lastCnt != 0)
            loadTimes ++ ;
        for(int i = 0 ; i < loadTimes; i ++){
            List<CruiseTrackEntity> list = cruiseTrackDao.loadCruiseTrackParitical(ONCE_LOAD_RECORD_COUNT * i , ONCE_LOAD_RECORD_COUNT);
            for(CruiseTrackEntity trackEntity:list){
                cruiseTrackService.cruiseTrackMap.put(trackEntity.getTrackId(),trackEntity);
            }
        }

        /// 获取最大的 track_id
        cruiseTrackService.maxTrackId = cruiseTrackDao.getMaxTrackId();
        System.out.println("size:" + cruiseTrackService.cruiseTrackMap.size()) ;
    }
}
