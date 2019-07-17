package org.spring.springboot.domain;

public class DeviceEntity {

    public enum DEV_PREVIL{

        DEV_CONNECT         ,
        DEV_LOGIN           ,
        DEV_PREVIEW         ,
        DEV_PTZ_CTRL        ,
    };
    public class DevicePrevilege{

        private String devName;
        private DEV_PREVIL  devPrevil;
    }

}
