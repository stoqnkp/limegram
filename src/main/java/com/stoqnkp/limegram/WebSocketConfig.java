package com.stoqnkp.limegram;

import com.stoqnkp.limegram.service.PrivateFeedService;
import com.stoqnkp.limegram.service.PublicFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    PrivateFeedService privateFeedService;

    @Autowired
    PublicFeedService publicFeedService;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(privateFeedService, "/ws/privateFeed").setAllowedOrigins("*");
        registry.addHandler(publicFeedService, "/ws/publicFeed").setAllowedOrigins("*");
    }
}
