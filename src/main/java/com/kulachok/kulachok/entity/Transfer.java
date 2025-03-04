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

    @Column(name = "transfer_date", nullable = false)
    private LocalDateTime transferDate = LocalDateTime.now();

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "cashid", nullable = false)
    private Cash cashAccount;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "actrissid")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Actris actris;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "userid")
    private User user;
}
