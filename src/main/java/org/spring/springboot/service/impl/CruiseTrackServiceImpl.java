package org.spring.springboot.service.impl;

import org.spring.springboot.dao.CruiseTrackDao;
import org.spring.springboot.domain.CruiseTrackEntity;
import org.spring.springboot.service.ICruiseTrackService;
import org.spring.springboot.utils.SGCCConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class CruiseTrackServiceImpl implements ICruiseTrackService {

    public long                             maxTrackId = 0;
    public int                              maxFuncNo = 0;

    @Autowired
    private CruiseTrackDao cruiseTrackDao;
    @Override
    public Integer getMaxFuncNo() {
        return cruiseTrackDao.getMaxFuncNo();
    }

    @Override
    public Long getTrackId() {
        return cruiseTrackDao.getTrackId();
    }


    /*
    * 根据设备id，功能号，时间戳 生成 巡航轨迹id （trackId）
    * */
    @Override
    public Long generateTaskId(CruiseTrackEntity cruiseTrack) {

        long ts = Calendar.getInstance().getTimeInMillis() / SGCCConstant.m_total_sec_one_day;

        return null;
    }


    @Override
    public int addNewCruiseTrack(long trackId, String trackName, long devId, int funcNo, String trackPointList, Integer policy) {

        CruiseTrackEntity cruiseTrack = new CruiseTrackEntity();

        cruiseTrack.setTrackId(trackId);
        cruiseTrack.setTrackName(trackName);
        cruiseTrack.setDevId(devId);
        cruiseTrack.setFuncNo(funcNo);
        cruiseTrack.setPresetPositionQueue(trackPointList);
        cruiseTrack.setCruisePolicy(policy);
        cruiseTrack.setCreateTime(Calendar.getInstance().getTimeInMillis());
        cruiseTrack.setUpdateTime(Calendar.getInstance().getTimeInMillis());

        Integer result = cruiseTrackDao.addNewCruiseTrack(cruiseTrack);

        return result;
    }

    @Override
    public List<CruiseTrackEntity> findAllCruiseTrackByDevId(long devId) {
        List<CruiseTrackEntity> list = cruiseTrackDao.findAllCruiseTrackByDevId(devId);
        return list;
    }
}
