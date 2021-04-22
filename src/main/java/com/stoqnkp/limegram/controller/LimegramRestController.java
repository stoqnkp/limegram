package com.stoqnkp.limegram.controller;

import com.stoqnkp.limegram.events.GetFeedEvent;
import com.stoqnkp.limegram.events.RequestUploadEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class LimegramRestController {

    private final ApplicationEventPublisher eventPublisher;

    public LimegramRestController(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @GetMapping("/feed/public")
    public DeferredResult<List<byte[]>> getPublicFeed() {
        DeferredResult<List<byte[]>> deferredResult = new DeferredResult<>();
        eventPublisher.publishEvent(new GetFeedEvent(this, deferredResult, null));
        return deferredResult;
    }

    @GetMapping("/feed/{userId}")
    public DeferredResult<List<byte[]>> getUserFeed(@PathVariable String userId) {
        DeferredResult<List<byte[]>> deferredResult = new DeferredResult<>();
        eventPublisher.publishEvent(new GetFeedEvent(this, deferredResult, userId));
        return deferredResult;
    }

    @PostMapping("/upload/{userId}")
    public void uploadImage(@PathVariable String userId, @RequestParam("file") MultipartFile file) {
        try {
            RequestUploadEvent requestUploadEvent = new RequestUploadEvent(this, userId, file.getBytes());
            eventPublisher.publishEvent(requestUploadEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
