package com.kulachok.kulachok.entity;


import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "videos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Video {
    @Id
    private Long id;
    private String title;
    private String description;
    private String url;
    private int duration; // в секундах
    private LocalDateTime creationDate;
    private Long authorId;
    private String category;
    @ElementCollection
    private List<String> tags;
    private int viewCount;
    private int likeCount;
    private int dislikeCount;
    private String status;
    private String thumbnailUrl;
    private String format;
}
