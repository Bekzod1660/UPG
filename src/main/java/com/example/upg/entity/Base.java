package com.example.upg.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.sql.Timestamp;

@Getter
@Setter
@MappedSuperclass
public abstract class Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;


    @CreatedBy
    protected String createdBy;

    @LastModifiedBy
    protected String updatedBy;


    @CreationTimestamp
    protected Timestamp createdDate;

    @UpdateTimestamp
    protected Timestamp updatedDate;
}
