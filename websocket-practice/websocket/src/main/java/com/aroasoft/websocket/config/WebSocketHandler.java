package com.aroasoft.websocket.config;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {
    private HashMap<String, WebSocketSession> map = new HashMap<>();
    private HttpSession httpSession;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        map.put(httpSession.getAttribute("username"), session);
        log.info("클라이언트 접속 : {}", session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload : {}", payload);

        // 전체 전송
        for (String key : map.keySet()) {
            WebSocketSession wss = map.get(key);
            wss.sendMessage(message);
        }
        
        // 특정 유저에게 전송
        String userId = null;
        WebSocketSession wss = map.get(userId);
        wss.sendMessage(message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("{} - 클라이언트 접속 해제", session);
        map.remove(session.getId());
    }
}
