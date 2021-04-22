package com.stoqnkp.limegram.service;

import com.stoqnkp.limegram.websockets.PrivateFeedHandler;
import com.stoqnkp.limegram.websockets.PublicFeedHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ImageService {

    Map<String, List<byte[]>> userIdToImageMap = new HashMap<>();
    List<byte[]> publicFeed = new ArrayList<>();

    private final PrivateFeedHandler privateFeedHandler;
    private final PublicFeedHandler publicFeedHandler;

    public ImageService(PrivateFeedHandler privateFeedHandler, PublicFeedHandler publicFeedHandler) {
        this.privateFeedHandler = privateFeedHandler;
        this.publicFeedHandler = publicFeedHandler;
    }

    public List<byte[]> getAllImages() {
        return publicFeed;
    }

    public List<byte[]> getUserImages(String userId) {
        return userIdToImageMap.get(userId);
    }

    public void uploadImage(String userId, byte[] file){
        if(!userIdToImageMap.containsKey(userId)) {
            List<byte[]> userImages = new ArrayList<>();
            userImages.add(file);
            userIdToImageMap.put(userId, userImages);
        } else {
            userIdToImageMap.get(userId).add(file);
        }
        publicFeed.add(file);

        privateFeedHandler.sendMessageToUserSessions(userId, file);
        publicFeedHandler.sendMessageToAllSessions(file);
    }

}
