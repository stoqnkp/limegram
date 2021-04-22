package com.stoqnkp.limegram.websockets;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PrivateFeedHandler extends TextWebSocketHandler {

    private Map<String, WebSocketSession> sessions = new HashMap<>();
    private Map<String, List<String>> userToSessionMap = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.put(session.getId(), session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        System.out.println("Received new session with id: " + session.getId() + " message " + message.getPayload());

        if(message.getPayload() == null || message.getPayload().isEmpty()) {
            return;
        } else if(!userToSessionMap.containsKey(message.getPayload())) {
            userToSessionMap.put(message.getPayload(), new ArrayList<>());
            userToSessionMap.get(message.getPayload()).add(session.getId());
        } else if(!userToSessionMap.get(message.getPayload()).contains(session.getId())) {
            userToSessionMap.get(message.getPayload()).add(session.getId());
        }
    }

    public void sendMessageToUserSessions(String userName, String message) {
        List<String> sessionsList = userToSessionMap.get(userName);
        if(sessionsList == null || sessionsList.isEmpty())
            return;
        for(String sessionId: sessionsList) {
            WebSocketSession session = sessions.get(sessionId);
            if(session != null && session.isOpen())
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    e.printStackTrace();
                };
        }
    }

}