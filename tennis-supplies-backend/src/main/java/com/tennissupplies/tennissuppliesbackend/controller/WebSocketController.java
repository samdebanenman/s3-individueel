package com.tennissupplies.tennissuppliesbackend.controller;

import io.micrometer.common.lang.NonNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PingMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.nio.ByteBuffer;
import java.util.HashSet;

@Controller
public class WebSocketController extends TextWebSocketHandler {
    
    static HashSet<WebSocketSession> sessions = new HashSet<>();

        @Override
        public void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) throws Exception {
            super.handleTextMessage(session, message);
        }

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session,@NonNull CloseStatus status) throws Exception {
        sessions.remove(session);
        super.afterConnectionClosed(session, status);
    }

    public void notifyProductChange() {
            for (WebSocketSession session : sessions) {
                try {
                    session.sendMessage(new TextMessage("Product changed"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    @Scheduled(fixedRate = 30000)
    public void sendHeartbeat() {
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new PingMessage(ByteBuffer.wrap(new byte[]{})));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
