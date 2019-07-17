package org.spring.springboot.service.impl;

import org.spring.springboot.dao.UserManageDao;
import org.spring.springboot.domain.EmployeeEntity;
import org.spring.springboot.domain.UserEntity;
import org.spring.springboot.service.UserManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 城市业务逻辑实现类
 *
 * Created by bysocket on 07/02/2017.
 */
@Service
public class UserManageServiceImpl implements UserManageService {

    @Autowired
    private UserManageDao userManageDao;

    public UserEntity findCityByName(String cityName) {
//        return userManageDao.findByName(cityName);
        return null;
    }

    public UserEntity.UserBasicInfo findBasicInfoByUserName(String userName) {
        return null;
    }

    @Override
    public List<HashMap<String,Object>> findBasicInfoByUserName2() {
        return userManageDao.findBasicInfoByUserName2();
    }

    @Override
    public void insertNewUserInfo(String userName, String userPasswd) {
        userManageDao.insertNewUserInfo(userName,userPasswd);
    }

    @Override
    public List<HashMap<String, Object>> loadAllUserInfo() {

        List<HashMap<String, Object>> list = userManageDao.loadAllUserInfo();
        return list;
    }

    @Override
    public List<HashMap<String, Object>> loadAllUserInfo8(){

        List<HashMap<String, Object>> list = userManageDao.loadAllUserInfo8();
        return list;
    }

    @Override
    public void addNewUserInfo8(String userName,String userPasswd) {
        userManageDao.addNewUserInfo8(userName,userPasswd);
    }

    @Override
    public int addNewEmployeeInfo8(EmployeeEntity info) {
        int ret = 0 ;
        try{
            ret = userManageDao.addNewEmployeeInfo8(info);
        }
        catch(Exception ex)
        {
//            System.out.println("msg:" +ex.getMessage());
        }
        return ret;
    }
}
