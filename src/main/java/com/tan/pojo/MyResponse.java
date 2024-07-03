package com.tan.pojo;

import lombok.Data;

@Data
public class MyResponse {
    private static final String STATUS_OK = "200";
    private static final String STATUS_ERROR = "500";
    private String code;
    private Object data;

    public MyResponse(String code, Object data){
        this.code = code;
        this.data = data;
    }

    public static MyResponse ok(Object data){
        return new MyResponse(STATUS_OK, data);
    }

    public static MyResponse error(String msg){
        return new MyResponse(STATUS_ERROR, msg);
    }
}
