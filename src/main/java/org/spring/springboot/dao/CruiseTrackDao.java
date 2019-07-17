package org.spring.springboot.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.spring.springboot.domain.CruiseTrackEntity;

import java.util.List;

@Mapper
public interface CruiseTrackDao {

    public Integer getMaxFuncNo();
    public Long getTrackId();
    public Integer addNewCruiseTrack(@Param("cruiseTrack")CruiseTrackEntity cruiseTrackEntity);

    public List<CruiseTrackEntity> findAllCruiseTrackByDevId(@Param("devId") long devId);
}
