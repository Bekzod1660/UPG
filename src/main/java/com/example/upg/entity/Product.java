package com.example.upg.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Product extends Base {

    @Column(unique = true, nullable = false)
    private String name;

    private String info;
    @Column(nullable = false)
    private double prays;

    @Column(nullable = false)
    private int quantity;

    private boolean isAction;

    @Column(nullable = false)
    private String madel;
    @Column(nullable = false)
    private String image;
    @ManyToOne
    private Category category;

}
