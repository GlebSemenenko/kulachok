package com.kulachok.kulachok.entity;


import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "videos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Video {
    /**
     * Уникальный идентификатор видео.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * Идентификатор автора или пользователя, загрузившего видео.
     */
    @Column(name = "author_id")
    private Integer authorId;

    /**
     * Список тегов, связанных с видео.
     */
    @ElementCollection
    @CollectionTable(name = "video_tags", joinColumns = @JoinColumn(name = "video_id"))
    @Column(name = "tag")
    private List<String> tags;

    /**
     * Объект, содержащий информацию о названии, описании и дате создания видео.
     */
    @Embedded
    private Identification identification;

    /**
     * Объект, содержащий статистику просмотров, лайков и дизлайков.
     */
    @Embedded
    private StatisticsVideo statistics;

    /**
     * Объект, содержащий информацию о URL, длительности и формате видео.
     */
    @Embedded
    private Content content;

}