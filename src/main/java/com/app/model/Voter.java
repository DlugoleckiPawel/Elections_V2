package com.app.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "voter")
@Entity
public class Voter {
    @Id
    @GeneratedValue
    private Long id;

    private Integer pesel;
    private Integer age;
    @Enumerated(EnumType.STRING)
    private Education education;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "constituency")
    private Constituency constituency;
}
