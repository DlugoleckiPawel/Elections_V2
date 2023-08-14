package com.app.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "political_party")
@Entity
public class PoliticalParty {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "politicalParty", fetch = FetchType.EAGER)
    private Set<Candidate> candidates = new HashSet<>();
}
