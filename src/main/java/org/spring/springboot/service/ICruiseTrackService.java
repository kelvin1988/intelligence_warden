package org.spring.springboot.service;

import org.spring.springboot.domain.CruiseTrackEntity;

import java.util.List;

public interface ICruiseTrackService {


    public Integer getMaxFuncNo();

    public Long getTrackId();

    public Long generateTaskId(CruiseTrackEntity cruiseTrack);

    public int addNewCruiseTrack(long trackId, String trackName, long devId, int funcNo, String trackPointList, Integer policy);

    public List<CruiseTrackEntity> findAllCruiseTrackByDevId(long devId);
}
