package com.stoqnkp.limegram.websockets;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PublicFeedHandler extends BinaryWebSocketHandler {

    private List<WebSocketSession> sessionsList = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionsList.add(session);
    }

    public void sendMessageToAllSessions(byte[] message) {
        if (sessionsList == null || sessionsList.isEmpty())
            return;
        for (WebSocketSession session : sessionsList) {
            if (session != null && session.isOpen())
                try {
                    session.sendMessage(new BinaryMessage(message));
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

}
