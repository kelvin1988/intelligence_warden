package org.spring.springboot.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 城市实体类
 *
 * Created by bysocket on 07/02/2017.
 */
public class UserEntity {

    public static class UserBasicInfo{

        private String userName;
        private String userPasswd;

        public UserBasicInfo() {
        }

        public UserBasicInfo(String userName, String userPasswd) {
            this.userName = userName;
            this.userPasswd = userPasswd;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserPasswd() {
            return userPasswd;
        }

        public void setUserPasswd(String userPasswd) {
            this.userPasswd = userPasswd;
        }
    }

    public class UserPrevilege{

        private String roleName;
        private String roleDescript;
        private List<DeviceEntity.DevicePrevilege> devPrevilList ;

        public UserPrevilege() {

            devPrevilList = new ArrayList<>();
        }
    }
    private UserBasicInfo userBasicInfo;
    private List<UserPrevilege> userPrevilList;

    public UserEntity(){
        userPrevilList = new ArrayList<>();
    }
}
