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
import java.util.ArrayList;
import java.util.NoSuchElementException;

import static util.UtilEntity.checkFieldForNullException;
import static util.UtilEntity.checkFieldForNullOrEmpty;
import static util.UtilEntity.checkFieldForNullOrEmptyException;

@Service
public class VideoServiceImpl implements VideoService {
    private final VideoRepository videoRepository;

    @Autowired
    public VideoServiceImpl(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @Override
    public Video addVideo(Integer id, VideoDto videoDto) {
        Video video = new Video();

        mappedDataVideo(id, videoDto, video);
        video.getIdentification().setCreationDate(LocalDateTime.now());

        return videoRepository.save(video);
    }

    @Override
    public Video updateVideo(Integer id, VideoDto videoDto) {
        Video existingVideo = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        mappedDataVideo(id, videoDto, existingVideo);


        return videoRepository.save(existingVideo);
    }

    @Override
    public void deleteVideo(Integer id) {
        if (!videoRepository.existsById(id)) {
            throw new NoSuchElementException("Video not found");
        }
        videoRepository.deleteById(id);
    }

    private void mappedDataVideo(Integer id, VideoDto videoDto, Video existingVideo) {
        Identification identification = existingVideo.getIdentification() != null
                ? existingVideo.getIdentification()
                : new Identification();
        StatisticsVideo statisticsVideo = existingVideo.getStatistics() != null
                ? existingVideo.getStatistics()
                : new StatisticsVideo();
        Content content = existingVideo.getContent() != null
                ? existingVideo.getContent()
                : new Content();

        existingVideo.setAuthorId(id);

        if (videoDto.getTags() != null) {
            existingVideo.setTags(new ArrayList<>(videoDto.getTags()));
        }

        if (videoDto.getIdentification() != null) {
            checkFieldForNullOrEmptyException(videoDto.getIdentification().getTitle(), "Title", identification::setTitle);
            checkFieldForNullOrEmpty(videoDto.getIdentification().getDescription(), "Description", identification::setDescription);
            checkFieldForNullOrEmptyException(videoDto.getIdentification().getStatus(), "Status", identification::setStatus);
            checkFieldForNullOrEmptyException(videoDto.getIdentification().getCategory(), "Category", identification::setCategory);
        }

        if (videoDto.getStatisticsVideo() != null) {
            checkFieldForNullException(videoDto.getStatisticsVideo().getViewCount(), "View Count", statisticsVideo::setViewCount);
            checkFieldForNullException(videoDto.getStatisticsVideo().getLikeCount(), "Like Count", statisticsVideo::setLikeCount);
            checkFieldForNullException(videoDto.getStatisticsVideo().getDislikeCount(), "Dislike Count", statisticsVideo::setDislikeCount);
        }

        if (videoDto.getContent() != null) {
            checkFieldForNullException(videoDto.getContent().getDuration(), "Duration", content::setDuration);
            checkFieldForNullOrEmptyException(videoDto.getContent().getUrl(), "URL", content::setUrl);
            checkFieldForNullOrEmpty(videoDto.getContent().getThumbnailUrl(), "Thumbnail URL", content::setThumbnailUrl);
            checkFieldForNullOrEmptyException(videoDto.getContent().getFormat(), "Format", content::setFormat);
        }

        existingVideo.setIdentification(identification);
        existingVideo.setStatistics(statisticsVideo);
        existingVideo.setContent(content);
    }
}
