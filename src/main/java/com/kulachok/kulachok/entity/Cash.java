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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "description")
    private String description;

    @Column(name = "transferDate")
    private LocalDateTime transferDate;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "actor_id")
    private Actor actor;

    @OneToMany(mappedBy = "cashAccount")
    @JsonIgnore
    private List<Transfer> transfers;
}


