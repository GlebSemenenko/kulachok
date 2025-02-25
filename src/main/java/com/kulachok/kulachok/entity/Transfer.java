package com.kulachok.kulachok.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transfer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "sum_transfer")
    private BigDecimal sumTransfer;

    @Column(name = "all_sum_transfer")
    private BigDecimal allSumTransfer;

    @Column(name = "transfer_date")
    private LocalDateTime transferDate;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnore
    private Cash cashAccount;

    @ManyToOne
    @JsonIgnore
    private Actris actris;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "userid")
    private User user;
}
