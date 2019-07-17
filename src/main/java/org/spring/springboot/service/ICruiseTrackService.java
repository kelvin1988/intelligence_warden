package org.spring.springboot.service;

import org.spring.springboot.domain.CruiseTrackEntity;
import org.spring.springboot.domain.CruiseTrackPolicyEnum;

import java.util.List;

public interface ICruiseTrackService {


    public Integer getMaxFuncNo();

    public Long getTrackId();

    public Long generateTaskId(CruiseTrackEntity cruiseTrack);

    public int addNewCruiseTrack(long trackId, String trackName, long devId, int funcNo, String trackPointList, CruiseTrackPolicyEnum policy);

    public List<CruiseTrackEntity> findAllCruiseTrackByDevId(long devId);
}
