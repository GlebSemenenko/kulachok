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

    @Column(name = "transferType")
    private String transferType;

    @Column(name = "transferDate")
    private LocalDateTime transferDate = LocalDateTime.now();

    @OneToOne(cascade = CascadeType.REMOVE)
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "actris_id")
    private Actris actris;

    @OneToMany(mappedBy = "cashAccount")
    @JsonIgnore
    private List<Transfer> transfers;
}


