package com.stoqnkp.limegram.events;

import org.springframework.context.ApplicationEvent;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;

public class GetPrivateFeedEvent extends ApplicationEvent {

    private final DeferredResult<List<byte[]>> result;
    private final String userId;

    public GetPrivateFeedEvent(Object source, DeferredResult<List<byte[]>> result, String userId) {
        super(source);
        this.result = result;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public DeferredResult<List<byte[]>> getResult() {
        return result;
    }
}
