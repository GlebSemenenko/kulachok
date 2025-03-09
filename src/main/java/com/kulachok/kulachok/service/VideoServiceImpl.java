package com.kulachok.kulachok.service;

import com.kulachok.kulachok.dto.VideoDto;
import com.kulachok.kulachok.entity.Content;
import com.kulachok.kulachok.entity.Identification;
import com.kulachok.kulachok.entity.StatisticsVideo;
import com.kulachok.kulachok.entity.Video;
import com.kulachok.kulachok.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class VideoServiceImpl implements VideoService {
    //todo добавить логирование при сиключения
    private final VideoRepository videoRepository;

    @Autowired
    public VideoServiceImpl(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @Override
    public Video addVideo(Integer id, VideoDto videoDto) {
        Video video = new Video();
        video.setAuthorId(id);
        video.setTags(videoDto.getTags());
        video.setIdentification(new Identification(
                videoDto.getIdentification().getTitle(),
                videoDto.getIdentification().getDescription(),
                LocalDateTime.now(),
                videoDto.getIdentification().getStatus(),
                videoDto.getIdentification().getCategory()
        ));
        video.setStatistics(new StatisticsVideo(
                videoDto.getStatisticsVideo().getViewCount(),
                videoDto.getStatisticsVideo().getLikeCount(),
                videoDto.getStatisticsVideo().getDislikeCount()
        ));
        video.setContent(new Content(
                videoDto.getContent().getDuration(),
                videoDto.getContent().getUrl(),
                videoDto.getContent().getThumbnailUrl(),
                videoDto.getContent().getFormat()
        ));
        return videoRepository.save(video);
    }

    @Override
    public Video updateVideo(Integer id, VideoDto videoDto) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found"));
        video.setTags(videoDto.getTags());
        video.getIdentification().setTitle(videoDto.getIdentification().getTitle());
        video.getIdentification().setDescription(videoDto.getIdentification().getDescription());
        video.getIdentification().setStatus(videoDto.getIdentification().getStatus());
        video.getIdentification().setCategory(videoDto.getIdentification().getCategory());
        video.getStatistics().setViewCount(videoDto.getStatisticsVideo().getViewCount());
        video.getStatistics().setLikeCount(videoDto.getStatisticsVideo().getLikeCount());
        video.getStatistics().setDislikeCount(videoDto.getStatisticsVideo().getDislikeCount());
        video.getContent().setDuration(videoDto.getContent().getDuration());
        video.getContent().setUrl(videoDto.getContent().getUrl());
        video.getContent().setThumbnailUrl(videoDto.getContent().getThumbnailUrl());
        video.getContent().setFormat(videoDto.getContent().getFormat());

        return videoRepository.save(video);
    }

    @Override
    public void deleteVideo(Integer id) {
        if (!videoRepository.existsById(id)) {
            throw new NoSuchElementException("Video not found");
        }
        videoRepository.deleteById(id);

    }
}
