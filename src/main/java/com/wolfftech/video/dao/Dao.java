package com.wolfftech.video.dao;

import com.wolfftech.video.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Dao extends JpaRepository<Video, Long> {


}
