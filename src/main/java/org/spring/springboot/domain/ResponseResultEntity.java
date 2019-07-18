package org.spring.springboot.domain;

import java.util.HashMap;

/*
* 向客户端返回请求结果的统一结构体
* */
public class ResponseResultEntity {

    private int status;                 // 单次请求的执行状态；0 为正常，非0 为异常；客户端根据此字段 判断 此次请求的结果是否有效
    private String msg;                 // 附加信息，便于阅读
    private HashMap<String,Object> data;            // 请求的返回值可能带有附加数据

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }
}
