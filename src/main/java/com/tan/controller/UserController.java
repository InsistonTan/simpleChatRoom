package com.tan.controller;

import com.tan.pojo.MyResponse;
import com.tan.pojo.User;
import com.tan.websocket.ChatEndPoint;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/")
public class UserController {
    public static final String USERNAME = "username";

    @RequestMapping("/login")
    public MyResponse login(@RequestBody User user, HttpSession httpSession){
        if(!StringUtils.isEmpty(user.getUsername())){
            if(ChatEndPoint.existsUsername(user.getUsername())){
                return MyResponse.error("该用户名已被使用");
            }

            httpSession.setAttribute(USERNAME, user.getUsername());
            user.setPassword("******");
            return MyResponse.ok(user);
        }

        return MyResponse.error("用户名不能为空");
    }
}
