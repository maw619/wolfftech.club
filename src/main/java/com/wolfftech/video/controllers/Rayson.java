package com.wolfftech.video.controllers;

import com.wolfftech.video.model.Video;
import com.wolfftech.video.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Rayson {

    @Autowired
    private HomeService homeService;

    @GetMapping("/rest")
    public List<Video> data(){

        List<Video>listar = homeService.getAllVideos();
        return listar;
    }
}
