package org.spring.springboot.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.spring.springboot.domain.EmployeeEntity;
import org.spring.springboot.domain.UserEntity;

import java.util.HashMap;
import java.util.List;

/**
 * 城市 DAO 接口类
 *
 * Created by bysocket on 07/02/2017.
 */
@Mapper
public interface UserManageDao {

    /**
     * 根据城市名称，查询城市信息
     *
     * @param cityName 城市名
     */
//    UserEntity findByName(@Param("cityName") String cityName);
//
//    UserEntity.UserBasicInfo findBasicInfoByUserName(@Param("userName") String userName);

    List<HashMap<String,Object>> findBasicInfoByUserName2();

    void insertNewUserInfo(@Param("userName") String userName,@Param("userPasswd") String userPasswd);

    List<HashMap<String,Object>> loadAllUserInfo();

    List<HashMap<String,Object>> loadAllUserInfo8();

    void addNewUserInfo8(@Param("userName") String userName,@Param("userPasswd") String userPasswd);

    int addNewEmployeeInfo8(@Param("employeeInfo")EmployeeEntity employeeInfo);
}
