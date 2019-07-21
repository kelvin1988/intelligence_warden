package org.spring.springboot.service.impl;

import org.spring.springboot.dao.CruiseTrackDao;
import org.spring.springboot.domain.CruiseTrackEntity;
import org.spring.springboot.domain.PresetPositionEntity;
import org.spring.springboot.service.ICruiseTrackService;
import org.spring.springboot.utils.SGCCConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Service
public class CruiseTrackServiceImpl implements ICruiseTrackService {

    public final long                                   MAX_TRACK_ID = 30000;
    public long                                         maxTrackId = 0;
    public int                                          maxFuncNo = 0;

    /*
    * map 的key为轨迹id，value为轨迹对象
    * */
    public HashMap<Long,CruiseTrackEntity>              cruiseTrackMap = new HashMap<>();

    @Autowired
    private CruiseTrackDao cruiseTrackDao;
    @Override
    public Integer getMaxFuncNo() {
        return cruiseTrackDao.getMaxFuncNo();
    }

    @Override
    public Long getMaxTrackId() {
        return cruiseTrackDao.getMaxTrackId();
    }

    /*
    * 表中存放的预置位信息为字符串，按照如下格式编码: 预置位1编号，预置位1名称，云台转动时间，相机对焦时间，相机采样时间；预置位2编号，预置位2名称，云台转动时间，相机对焦时间，相机采样时间；.......
    * 将字符串解析为预置位对象列表
    * 参数1： 按照上述编码的字符串
    * 参数2： 考虑到以后可能会用不同的参数来描述一个预置位，因此，逗号分隔的字符串的字段个数可能会变，比如当前格式下，每个预置位有5个字段；若添加预置位的旋转角度，对焦深度等参数，每个预置位可能会有8个字段
    * */
    public List<PresetPositionEntity> parsePresetPositionStr2List(String presetPositionStr,int fieldCnt){

        List<PresetPositionEntity> list = null;
        String[] presetPosArray = presetPositionStr.split("\\;");
        list = new ArrayList<>();
        for(String presetPosStr:presetPosArray ){

            if(presetPosStr.isEmpty())
                continue;

            String[] fieldsArray = presetPosStr.split("\\,");

            if(fieldsArray.length != fieldCnt) {
                return null;
            }

            PresetPositionEntity presetPosEntity = new PresetPositionEntity();
            for(int i = 0 ; i < fieldsArray.length ; i ++){
                presetPosEntity.setPresetNo(Integer.parseInt(fieldsArray[0]));          // 字段1 为预置位编号
                presetPosEntity.setName(fieldsArray[1]);                                // 字段2 为预置位名称
                presetPosEntity.setRotateSeconds(Integer.parseInt(fieldsArray[2]));     // 字段3 为云台运动到预置位的时间
                presetPosEntity.setFocusSeconds(Integer.parseInt(fieldsArray[3]));     // 字段4 为相机对焦时间
                presetPosEntity.setSampleSeconds(Integer.parseInt(fieldsArray[4]));     // 字段5 为相机拍照、采样时间
            }

            boolean ret = list.add(presetPosEntity);
            // 只有巡航轨迹的预置位，有一个添加到列表失败，则返回空列表
            if(ret == false)
                return null;
        }

        return list;
    }

    /*
     * 将预置位对象，按照如下编码方式，编码成字符串
     * 表中存放的预置位信息为字符串，按照如下格式编码: 预置位1编号，预置位1名称，云台转动时间，相机对焦时间，相机采样时间；预置位2编号，预置位2名称，云台转动时间，相机对焦时间，相机采样时间；.......
     * 参数1： 预置位对象列表
     * 参数2： 考虑到以后可能会用不同的参数来描述一个预置位，因此，逗号分隔的字符串的字段个数可能会变，比如当前格式下，每个预置位有5个字段；若添加预置位的旋转角度，对焦深度等参数，每个预置位可能会有8个字段
     * */
    public String parsePresetPositionStr2List(List<PresetPositionEntity> list, int fieldCnt){
        String presetPositionStr = "";
        for(PresetPositionEntity presetPos : list){

            String str = presetPos.getPresetNo()+"," + presetPos.getName()+"," + presetPos.getRotateSeconds() + "," + presetPos.getRotateSeconds() + "," + presetPos.getSampleSeconds();
            presetPositionStr += str + ";";
        }
        return presetPositionStr;
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

        if(result > 0)
            cruiseTrackMap.put(trackId,cruiseTrack);
        return result;
    }

    @Override
    public List<CruiseTrackEntity> findAllCruiseTrackByDevId(long devId) {
        List<CruiseTrackEntity> list = cruiseTrackDao.findAllCruiseTrackByDevId(devId);
        return list;
    }
}
