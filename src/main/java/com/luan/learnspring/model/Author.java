package com.luan.learnspring.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "authors")
public class Author extends BaseEntity {

    @Column(nullable = false)
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    private Phone phone;

}
