package com.stoqnkp.limegram.events;

import org.springframework.context.ApplicationEvent;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;

public class GetPublicFeedEvent extends ApplicationEvent {

    private final DeferredResult<List<byte[]>> result;

    public GetPublicFeedEvent(Object source, DeferredResult<List<byte[]>> result) {
        super(source);
        this.result = result;
    }

    public DeferredResult<List<byte[]>> getResult() {
        return result;
    }
}
