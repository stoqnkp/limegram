package com.stoqnkp.limegram.service;

import com.stoqnkp.limegram.events.GetPrivateFeedEvent;
import com.stoqnkp.limegram.events.GetPublicFeedEvent;
import com.stoqnkp.limegram.events.RequestUploadEvent;
import com.stoqnkp.limegram.events.UploadedImageEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ImageService implements ApplicationListener<ApplicationEvent> {

    Map<String, List<byte[]>> userIdToImageMap = new HashMap<>();
    List<byte[]> publicFeed = new ArrayList<>();

    private final ApplicationEventPublisher eventPublisher;

    public ImageService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof RequestUploadEvent) {
            RequestUploadEvent requestUploadEvent = (RequestUploadEvent) applicationEvent;
            if (!userIdToImageMap.containsKey(requestUploadEvent.getUploaderId())) {
                List<byte[]> userImages = new ArrayList<>();
                userImages.add(requestUploadEvent.getImageBytes());
                userIdToImageMap.put(requestUploadEvent.getUploaderId(), userImages);
            } else {
                userIdToImageMap.get(requestUploadEvent.getUploaderId()).add(requestUploadEvent.getImageBytes());
            }
            publicFeed.add(requestUploadEvent.getImageBytes());

            // publish image uploaded event with user id. Public feed should always consume, private feed only if it has matching session (per user id)
            UploadedImageEvent uploadedImageEvent = new UploadedImageEvent(this, requestUploadEvent.getUploaderId(), requestUploadEvent.getImageBytes());
            eventPublisher.publishEvent(uploadedImageEvent);
        } else if (applicationEvent instanceof GetPublicFeedEvent) {
            ((GetPublicFeedEvent) applicationEvent).getResult().setResult(publicFeed);
        } else if (applicationEvent instanceof GetPrivateFeedEvent) {
            ((GetPrivateFeedEvent) applicationEvent).getResult().setResult(userIdToImageMap.get(((GetPrivateFeedEvent) applicationEvent).getUserId()));
        }
    }
}

