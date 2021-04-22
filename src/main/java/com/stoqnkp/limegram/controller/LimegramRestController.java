package com.stoqnkp.limegram.controller;

import com.stoqnkp.limegram.service.ImageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class LimegramRestController {

    private final ImageService imageService;


    public LimegramRestController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/feed/public")
    public List<String> getPublicFeed() {
        return imageService.getAllImages();
    }

    @GetMapping("/feed/{userId}")
    public List<String> getUserFeed(@PathVariable String userId) {
        return imageService.getUserImages(userId);
    }

    @PostMapping("/upload/{userId}")
    public void uploadImage(@PathVariable String userId, @RequestParam("file") MultipartFile file) {
        imageService.uploadImage(userId, file);
    }
}
