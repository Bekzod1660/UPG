package com.example.upg.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Magazine extends Base {

    @Column(nullable = false,unique = true)
    private String name;
    @Column(nullable = false)
    private int phoneNumber;
    @Column(nullable = false)
    private String workingTime;
    /////



}
