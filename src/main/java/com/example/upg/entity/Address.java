package com.example.upg.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false,unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Address parent;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "parent")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Address> parentId;

}
