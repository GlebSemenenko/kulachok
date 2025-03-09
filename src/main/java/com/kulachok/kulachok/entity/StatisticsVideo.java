package com.kulachok.kulachok.entity;

import jakarta.persistence.Embeddable;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class StatisticsVideo {
    /**
     * Количество просмотров видео.
     */
    @Column(name = "view_count")
    private int viewCount;

    /**
     * Количество лайков.
     */
    @Column(name = "like_count")
    private int likeCount;

    /**
     * Количество дизлайков.
     */
    @Column(name = "dislike_count")
    private int dislikeCount;
}
