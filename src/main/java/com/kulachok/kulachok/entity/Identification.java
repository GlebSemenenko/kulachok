package com.kulachok.kulachok.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Identification {
    /**
     * Название видео.
     */
    @Column(name = "title")
    private String title;

    /**
     * Краткое описание содержания видео.
     */
    @Column(name = "description")
    private String description;

    /**
     * Дата и время загрузки видео.
     */
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    /**
     * Статус видео.
     */
    @Column(name = "status")
    private String status;

    /**
     * Категория или жанр видео.
     */
    @Column(name = "category")
    private String category;


}



