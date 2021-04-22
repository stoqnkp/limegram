package com.stoqnkp.limegram.websockets;

import com.stoqnkp.limegram.events.UploadedImageEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PublicFeedHandler extends BinaryWebSocketHandler implements ApplicationListener<UploadedImageEvent> {

    private List<WebSocketSession> sessionsList = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionsList.add(session);
    }

    @Override
    public void onApplicationEvent(UploadedImageEvent uploadedImageEvent) {
        if (sessionsList == null || sessionsList.isEmpty())
            return;
        for (WebSocketSession session : sessionsList) {
            if (session != null && session.isOpen())
                try {
                    session.sendMessage(new BinaryMessage(uploadedImageEvent.getImageBytes()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
