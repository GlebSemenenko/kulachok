package com.kulachok.kulachok.service;

import com.kulachok.kulachok.dto.VideoDto;
import com.kulachok.kulachok.entity.Video;

public interface VideoService {
    Video addVideo(Integer id, VideoDto video);
    Video updateVideo(Integer id, VideoDto video);
    void deleteVideo(Integer id);
}
