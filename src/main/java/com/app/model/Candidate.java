package com.app.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "candidate")
public class Candidate {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String surname;
    private LocalDate birthday;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "constituency")
    public Constituency constituency;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "politicalParty")
    public PoliticalParty politicalParty;
}
