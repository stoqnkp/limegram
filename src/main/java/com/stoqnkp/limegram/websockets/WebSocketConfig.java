package com.stoqnkp.limegram.websockets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    PrivateFeedHandler privateFeedHandler;

    @Autowired
    PublicFeedHandler publicFeedHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(privateFeedHandler, "/ws/privateFeed").setAllowedOrigins("*");
        registry.addHandler(publicFeedHandler, "/ws/publicFeed").setAllowedOrigins("*");
    }
}
