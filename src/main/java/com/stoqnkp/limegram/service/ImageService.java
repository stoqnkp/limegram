package com.stoqnkp.limegram.service;

import com.stoqnkp.limegram.events.GetFeedEvent;
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
public class ImageService implements ApplicationListener<ApplicationEvent>{

    Map<String, List<byte[]>> userIdToImageMap = new HashMap<>();
    List<byte[]> publicFeed = new ArrayList<>();

    private ApplicationEventPublisher eventPublisher;

    public ImageService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if(applicationEvent instanceof RequestUploadEvent) {
            RequestUploadEvent requestUploadEvent = (RequestUploadEvent) applicationEvent;
            if(!userIdToImageMap.containsKey(requestUploadEvent.getUploaderId())) {
                List<byte[]> userImages = new ArrayList<>();
                userImages.add(requestUploadEvent.getImageBytes());
                userIdToImageMap.put(requestUploadEvent.getUploaderId(), userImages);
            } else {
                userIdToImageMap.get(requestUploadEvent.getUploaderId()).add(requestUploadEvent.getImageBytes());
            }
            publicFeed.add(requestUploadEvent.getImageBytes());

            // publish image uploaded event with user id. Public feed should always consume, private feed only if it has matching session
            UploadedImageEvent uploadedImageEvent = new UploadedImageEvent(this, requestUploadEvent.getUploaderId(), requestUploadEvent.getImageBytes());
            eventPublisher.publishEvent(uploadedImageEvent);
        } else if (applicationEvent instanceof GetFeedEvent) {
            GetFeedEvent getFeedEvent = (GetFeedEvent) applicationEvent;
            if(getFeedEvent.getUserId() == null) {
                getFeedEvent.getResult().setResult(publicFeed);
            } else {
                getFeedEvent.getResult().setResult(userIdToImageMap.get(getFeedEvent.getUserId()));
            }
            System.out.println("set result in deferred result object");
        }
    }
}
