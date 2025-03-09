package com.kulachok.kulachok.service;

import com.kulachok.kulachok.dto.VideoDto;
import com.kulachok.kulachok.entity.Video;

import java.util.List;

public interface VideoService {
    List<Video> getAllVideos();
    Video addVideo(Integer id, VideoDto video);
    Video updateVideo(Integer id, VideoDto video);
    void deleteVideo(Integer id);
}
