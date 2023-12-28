package com.aroasoft.websocket.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class WebSocketController {

    @GetMapping("/chat")
    public String chatGET() {
        log.info("@ChatController, chat GET()");
        return "chat";
    }
}
