package com.wolfftech.video.service;

import com.wolfftech.video.dao.Dao;
import com.wolfftech.video.model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Version;
import java.util.List;
import java.util.Optional;

@Service
public class HomeService {

    @Autowired
    private final Dao dao;


    public HomeService(Dao dao){
        this.dao = dao;
    }

    public List<Video> getAllVideos(){
        return dao.findAll();
    }

    public void addVideo(Video video){
        dao.save(video);
    }

    public void updateVideo(Video video){
        dao.save(video);
    }

    public Optional<Video> getVideoById(Long id){
        return dao.findById(id);
    }

    public void deleteById(Long id){
        dao.deleteById(id);
    }

}
