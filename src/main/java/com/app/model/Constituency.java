package com.app.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "constituency")
public class Constituency {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "constituency", fetch = FetchType.EAGER)
    private Set<Candidate> candidates = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "constituency", fetch = FetchType.EAGER)
    private Set<Voter> voters = new HashSet<>();
}
