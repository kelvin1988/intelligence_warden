package org.spring.springboot.controller;

import org.spring.springboot.domain.ResponseResultEntity;
import org.spring.springboot.domain.EmployeeEntity;
import org.spring.springboot.domain.UserEntity;
import org.spring.springboot.service.UserManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * Created by bysocket on 07/02/2017.
 */
@RestController
public class UserManageController {

    @Autowired
    private UserManageService userManageService;

    @RequestMapping(value = "/api/city", method = RequestMethod.GET)
    public UserEntity findOneCity(@RequestParam(value = "cityName", required = true) String cityName) {
        return userManageService.findCityByName(cityName);
    }

    @PostMapping(value={"/userManage/loginAuth"})
    @ResponseBody
    public ResponseResultEntity loginAuth(@RequestBody UserEntity.UserBasicInfo userBasicInfo){

        ResponseResultEntity response = new ResponseResultEntity();

        List<HashMap<String,Object>> list = userManageService.findBasicInfoByUserName2();
        for(HashMap<String,Object> item : list){
            for(String keyName : item.keySet()){
                System.out.println( keyName + ":" + item.get(keyName));
            }
        }

        if(userBasicInfo.getUserName().equals("ray")
            && userBasicInfo.getUserPasswd().equals("123456")){
            response.setStatus(0);
            response.setMsg("欢迎登陆SGCC智能守卫客户端");
        }else{
            response.setStatus(-1);
            response.setMsg("用户名或密码错误,请重新输入");
        }

        return response ;
    }

    /**
     * 用户管理 --- 用户管理 选项页
     * */
    @PostMapping(value={"/userManage/getAllUserInfo"})
    @ResponseBody
    public List<HashMap<String,Object>> getAllUserInfo(UserEntity.UserBasicInfo userBasicInfo){
//        List<HashMap<String,Object>> list = userManageService.findBasicInfoByUserName2();
//        for(HashMap<String,Object> item : list){
//            for(String keyName : item.keySet()){
//                System.out.println( keyName + ":" + item.get(keyName));
//            }
//        }

        List<HashMap<String,Object>> list = userManageService.loadAllUserInfo();

        return list ;
    }

    @PostMapping(value={"/userManage/addNewUserInfo"})
    @ResponseBody
    public void addNewUserInfo(@RequestBody UserEntity.UserBasicInfo userBasicInfo){

            userManageService.insertNewUserInfo(userBasicInfo.getUserName(),userBasicInfo.getUserPasswd());


    }

    @GetMapping(value = {"/userManage/findAllFromMySql8"})
    public List<HashMap<String,Object>> findAllDataFromMySQL8()
    {
        List<HashMap<String,Object>> list = userManageService.loadAllUserInfo8();

        for (HashMap<String,Object> item : list){

            for (String name : item.keySet()){
                System.out.println(name+ ":" + item.get(name));
            }
        }
        return list;
    }

    @GetMapping(value = {"/userManage/addNewUserFromMySql8"})
    public List<HashMap<String,Object>> addNewUserFromMySQL8(@RequestBody HashMap<String,Object> requestData)
    {

        return null;
    }

    @GetMapping(value = {"/userManage/addNewEmployee8"})
    public List<HashMap<String,Object>> addNewEmployee8(@RequestBody HashMap<String,Object> requestData)
    {
        EmployeeEntity info = new EmployeeEntity();
        info.setUserName("ray");
        info.setStatus(100000000);
        int ret = userManageService.addNewEmployeeInfo8(info);
        System.out.println("ret:" + ret);

        return null;
    }
}
