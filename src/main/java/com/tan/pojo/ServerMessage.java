package com.tan.pojo;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class ServerMessage {
    private boolean systemMsg;
    private String fromUser;
    private String toUser;
    private String message;

    public static String toServerMessage(boolean systemMsg, String fromUser, String toUser, String message){
        return JSON.toJSONString(new ServerMessage(systemMsg, fromUser, toUser, message));
    }

    public static String getUserListStr(Set<String> userSet){
        return JSON.toJSONString(userSet);
    }
}
