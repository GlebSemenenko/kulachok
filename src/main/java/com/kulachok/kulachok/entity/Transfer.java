package com.kulachok.kulachok.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transfer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transfer {
    /**
     * Уникальный идентификатор перевода.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Сумма перевода.
     */
    @Column(name = "sum_transfer")
    private BigDecimal sumTransfer;

    /**
     * Общая сумма переводов.
     */
    @Column(name = "all_sum_transfer")
    private BigDecimal allSumTransfer;

    /**
     * Дата и время перевода.
     */
    @Column(name = "transfer_date")
    private LocalDateTime transferDate;

    /**
     * Описание перевода.
     */
    @Column(name = "description")
    private String description;

    /**
     * Тип перевода.
     */
    @Column(name = "transferType")
    private String transferType;

    /**
     * Связанный объект наличных средств.
     */
    @ManyToOne
    @JsonIgnore
    private Cash cashAccount;

    /**
     * Связанный объект актера.
     */
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "actor_id")
    private Actor actor;

    /**
     * Связанный объект пользователя.
     */
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "userid")
    private User user;
}
