package com.tan.websocket.config;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * ServerEndpoint配置类，用于获取httpSession，将httpSession与websocket会话绑定（ServerEndpointConfig）
 */
public class GetHttpSessionConfig extends ServerEndpointConfig.Configurator {
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        sec.getUserProperties().put(HttpSession.class.getName(), request.getHttpSession());
    }
}
