package com.wolfftech.video.rest;

import com.wolfftech.video.model.Video;
import com.wolfftech.video.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/videos")
public class CustomerRestController {

    @Autowired
    private HomeService homeService;

    @GetMapping(path = "/list")
    public ResponseEntity<List<Video>> getVideos(){
        return new ResponseEntity<>(homeService.getAllVideos(), HttpStatus.OK);
    }

    @GetMapping(path = "/video/{id}")
    public ResponseEntity<Optional<Video>> getVideo(@PathVariable("id") Long id){
        return new ResponseEntity<>(homeService.getVideoById(id), HttpStatus.OK);
    }

//    @PostMapping
//    public ResponseEntity<Video> add(Video video){
//        homeService.addVideo(video);
//
//    }

}
