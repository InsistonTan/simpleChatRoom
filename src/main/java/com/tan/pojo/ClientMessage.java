package com.tan.pojo;

import com.alibaba.fastjson.JSON;
import lombok.Data;

@Data
public class ClientMessage {
    private String toUser;
    private String message;


    public static ClientMessage parse(String jsonString){
        return JSON.parseObject(jsonString, ClientMessage.class);
    }
}
