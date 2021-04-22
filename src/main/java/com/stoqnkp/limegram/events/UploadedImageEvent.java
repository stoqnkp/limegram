package com.stoqnkp.limegram.events;

import org.springframework.context.ApplicationEvent;

public class UploadedImageEvent extends ApplicationEvent {

    private String uploaderId;
    private byte[] imageBytes;

    public UploadedImageEvent(Object source, String uploaderId, byte[] imageBytes) {
        super(source);
        this.uploaderId = uploaderId;
        this.imageBytes = imageBytes;
    }

    public String getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(String uploaderId) {
        this.uploaderId = uploaderId;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }
}
