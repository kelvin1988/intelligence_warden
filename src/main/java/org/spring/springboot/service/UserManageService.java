package org.spring.springboot.service;

import org.apache.ibatis.annotations.Param;
import org.spring.springboot.domain.EmployeeEntity;
import org.spring.springboot.domain.UserEntity;

import java.util.HashMap;
import java.util.List;

/**
 * 城市业务逻辑接口类
 *
 * Created by bysocket on 07/02/2017.
 */
public interface UserManageService {

    /**
     * 根据城市名称，查询城市信息
     * @param cityName
     */
    UserEntity findCityByName(String cityName);

    UserEntity.UserBasicInfo findBasicInfoByUserName( String userName);

    List<HashMap<String,Object>> findBasicInfoByUserName2();

    void insertNewUserInfo(String userName,String userPasswd);

    List<HashMap<String,Object>> loadAllUserInfo();

    List<HashMap<String,Object>> loadAllUserInfo8();

    void addNewUserInfo8(String userName,String userPasswd);

    int addNewEmployeeInfo8(EmployeeEntity info);
}
