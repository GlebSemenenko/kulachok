package com.kulachok.kulachok.repository;

import com.kulachok.kulachok.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {
    List<Video> findByAuthorId(Integer authorId);
}
