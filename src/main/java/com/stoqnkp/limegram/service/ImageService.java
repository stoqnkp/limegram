package com.stoqnkp.limegram.service;

import com.stoqnkp.limegram.websockets.PrivateFeedHandler;
import com.stoqnkp.limegram.websockets.PublicFeedHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class ImageService {

    Map<String, List<String>> userIdToImageMap = new HashMap<>();
    List<String> publicFeed = new ArrayList<>();

    private final PrivateFeedHandler privateFeedHandler;
    private final PublicFeedHandler publicFeedHandler;

    public ImageService(PrivateFeedHandler privateFeedHandler, PublicFeedHandler publicFeedHandler) {
        this.privateFeedHandler = privateFeedHandler;
        this.publicFeedHandler = publicFeedHandler;
    }

    public List<String> getAllImages() {
        return publicFeed;
    }

    public List<String> getUserImages(String userId) {
        return userIdToImageMap.get(userId);
    }

    public void uploadImage(String userId, MultipartFile file){
        if(!userIdToImageMap.containsKey(userId)) {
            List<String> userImageNames = new ArrayList<>();
            userImageNames.add(file.getOriginalFilename());
            userIdToImageMap.put(userId, userImageNames);
        } else {
            userIdToImageMap.get(userId).add(file.getOriginalFilename());
        }
        publicFeed.add(file.getOriginalFilename());


        privateFeedHandler.sendMessageToUserSessions(userId, file.getOriginalFilename());
        publicFeedHandler.sendMessageToAllSessions(file.getOriginalFilename());
    }

}
