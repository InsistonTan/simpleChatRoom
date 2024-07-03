package com.tan.websocket;

import com.tan.controller.UserController;
import com.tan.pojo.ClientMessage;
import com.tan.pojo.ServerMessage;
import com.tan.websocket.config.GetHttpSessionConfig;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
// configurator = GetHttpSessionConfig.class，用于指定自定义配置类
@ServerEndpoint(value = "/chat", configurator = GetHttpSessionConfig.class)
public class ChatEndPoint {
    // 房间所有在线用户
    private static final Map<String, Session> onlineUserMap = new ConcurrentHashMap<>();

    // 当前登录用户
    private String username;

    public static boolean existsUsername(String username) {
        return onlineUserMap.containsKey(username);
    }

    /**
     * websocket会话建立成功时执行
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        // 从EndpointConfig中获取httpSession
        HttpSession httpSession = (HttpSession) endpointConfig.getUserProperties().get(HttpSession.class.getName());
        // 从httpSession获取当前用户名
        this.username = (String) httpSession.getAttribute(UserController.USERNAME);

        // 将当前用户加入房间
        onlineUserMap.put(username, session);

        // 广播当前在线用户列表给房间所有成员
        fanout(true, null, null, ServerMessage.getUserListStr(onlineUserMap.keySet()));
    }

    /**
     * 有消息从客户端发过来时执行
     */
    @OnMessage
    public void onMessage(String message) {
        ClientMessage clientMessage = ClientMessage.parse(message);
        // 广播普通消息给房间所有成员
        fanout(false, username, null, clientMessage.getMessage());
    }

    /**
     * 会话关闭时执行
     */
    @OnClose
    public void onClose(Session session) {
        onlineUserMap.remove(username);

        // 广播当前在线用户列表给房间所有成员
        fanout(true, null, null, ServerMessage.getUserListStr(onlineUserMap.keySet()));
    }

    @OnError
    public void onError(Session session, Throwable throwable) {

    }

    /**
     * 广播消息给所有在线用户
     *
     * @param isSystemMsg 是否是系统通知
     * @param message     消息内容
     */
    private void fanout(boolean isSystemMsg, String fromUser, String toUser, String message) {
        onlineUserMap.values().forEach(session -> {
            // getAsyncRemote，代表使用异步的方式发送
            session.getAsyncRemote().sendText(ServerMessage.toServerMessage(isSystemMsg, fromUser, toUser, message));
        });
    }
}
