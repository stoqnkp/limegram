package com.stoqnkp.limegram.events;

import org.springframework.context.ApplicationEvent;

public class RequestUploadEvent extends ApplicationEvent {

    private final String uploaderId;
    private final byte[] imageBytes;

    public RequestUploadEvent(Object source, String uploaderId, byte[] imageBytes) {
        super(source);
        this.uploaderId = uploaderId;
        this.imageBytes = imageBytes;
    }

    public String getUploaderId() {
        return uploaderId;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }
}
