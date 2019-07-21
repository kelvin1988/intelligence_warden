package org.spring.springboot.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.spring.springboot.domain.CruiseTrackEntity;

import java.util.List;

@Mapper
public interface CruiseTrackDao {

    public Integer getMaxFuncNo();
    public Long getMaxTrackId();
    public Integer addNewCruiseTrack(@Param("cruiseTrack")CruiseTrackEntity cruiseTrackEntity);

    public List<CruiseTrackEntity> findAllCruiseTrackByDevId(@Param("devId") long devId);

    /*
    * 获取cruise_track_tb 表中 记录的总条数；在程序初始化时，需要先知道总共有多少条记录，然后每次从数据库读取1000条，逐次加载记录；
    * */
    public Long getTotalRecordsOfCruiseTrack();

    /*
    * 从cruise_track_tb 中加载count条记录；
    * */
    public List<CruiseTrackEntity> loadCruiseTrackParitical(@Param("startRowId") int startRowId,@Param("count") int count);
}
