package com.stoqnkp.limegram.websockets;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PublicFeedHandler extends TextWebSocketHandler {


    private List<WebSocketSession> sessionsList = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionsList.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        System.out.println("Public feed handler received new session with id: " + session.getId() + " message " + message.getPayload());
    }

    public void sendMessageToAllSessions(String message) {
        if(sessionsList == null || sessionsList.isEmpty())
            return;
        for(WebSocketSession session: sessionsList) {
            if(session != null && session.isOpen())
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

}
