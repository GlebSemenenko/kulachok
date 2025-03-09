package com.kulachok.kulachok.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Content {
    /**
     * Длительность видео в секундах.
     */
    @Column(name = "duration")
    private Integer duration;

    /**
     * Ссылка на видеофайл или поток.
     */
    @Column(name = "url")
    private String url;

    /**
     * Ссылка на изображение миниатюры видео.
     */
    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    /**
     * Формат видео (например, "MP4", "AVI").
     */
    @Column(name = "format")
    private String format;
}
