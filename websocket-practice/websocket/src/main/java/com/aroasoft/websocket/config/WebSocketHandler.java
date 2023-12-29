package com.aroasoft.websocket.config;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {
    private Map<String, WebSocketSession> map = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String username = (String) session.getAttributes().get("username");

        if (username != null) {
            map.put(username, session);
            log.info("클라이언트 접속 : {}", session);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload : {}", payload);

        String username  = (String) session.getAttributes().get("username");
        
        // 특정 유저에게 전송
        if (payload.contains("/whisper ")) {
            String[] msg = payload.split("/whisper ");
            WebSocketSession wss = map.get(msg[0]);
            wss.sendMessage(new TextMessage(("[귓속말]" + username + " : " + msg[1])));
        // 전체 유저에게 전송
        } else {
            for (WebSocketSession wss : map.values()) {
                wss.sendMessage(new TextMessage(username + " : " + payload));
            }
        }
    }
    
    // 월요일에 수정
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        for (Map.Entry<String, WebSocketSession> entry : map.entrySet()) {
            if (entry.getValue().equals(session)) {
                map.remove(entry.getKey());
                log.info("{} - 클라이언트 접속 해제", session);
            }
        }
    }
}
