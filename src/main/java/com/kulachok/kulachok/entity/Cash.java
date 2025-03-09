package com.kulachok.kulachok.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Cash")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cash {
    /**
     * Уникальный идентификатор записи о деньгах.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Сумма денег.
     */
    @Column(name = "amount")
    private BigDecimal amount;

    /**
     * Описание транзакции.
     */
    @Column(name = "description")
    private String description;

    /**
     * Дата и время перевода.
     */
    @Column(name = "transferDate")
    private LocalDateTime transferDate;

    /**
     * Связанный объект пользователя.
     */
    @OneToOne(cascade = CascadeType.REMOVE)
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Связанный объект актера.
     */
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "actor_id")
    private Actor actor;

    /**
     * Список переводов, связанных с этим счетом.
     */
    @OneToMany(mappedBy = "cashAccount")
    @JsonIgnore
    private List<Transfer> transfers;
}


