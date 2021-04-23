package com.stoqnkp.limegram.service;

import com.stoqnkp.limegram.events.UploadedImageEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PrivateFeedService extends BinaryWebSocketHandler implements ApplicationListener<UploadedImageEvent> {

    private final Map<String, WebSocketSession> sessions = new HashMap<>();
    private final Map<String, List<String>> userToSessionMap = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.put(session.getId(), session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        System.out.println("Received new session with id: " + session.getId() + " from user id " + message.getPayload());

        if (message.getPayload().isEmpty()) {
            return;
        }

        if (!userToSessionMap.containsKey(message.getPayload())) {
            userToSessionMap.put(message.getPayload(), new ArrayList<>());
            userToSessionMap.get(message.getPayload()).add(session.getId());
        } else if (!userToSessionMap.get(message.getPayload()).contains(session.getId())) {
            userToSessionMap.get(message.getPayload()).add(session.getId());
        }
    }

    @Override
    public void onApplicationEvent(UploadedImageEvent uploadedImageEvent) {
        if (userToSessionMap.containsKey(uploadedImageEvent.getUploaderId())) {
            List<String> sessionsList = userToSessionMap.get(uploadedImageEvent.getUploaderId());
            if (sessionsList == null || sessionsList.isEmpty())
                return;
            for (String sessionId : sessionsList) {
                WebSocketSession session = sessions.get(sessionId);
                if (session != null && session.isOpen())
                    try {
                        session.sendMessage(new BinaryMessage(uploadedImageEvent.getImageBytes()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
    }
}