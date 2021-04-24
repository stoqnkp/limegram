package com.stoqnkp.limegram;

import com.stoqnkp.limegram.service.PublicFeedService;
import com.stoqnkp.limegram.service.UserFeedService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final UserFeedService userFeedService;

    private final PublicFeedService publicFeedService;

    public WebSocketConfig(UserFeedService userFeedService, PublicFeedService publicFeedService) {
        this.userFeedService = userFeedService;
        this.publicFeedService = publicFeedService;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(userFeedService, "/ws/privateFeed").setAllowedOrigins("*");
        registry.addHandler(publicFeedService, "/ws/publicFeed").setAllowedOrigins("*");
    }
}
