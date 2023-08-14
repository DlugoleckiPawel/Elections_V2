package com.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "token")
@Entity
public class Token {
    @Id
    @GeneratedValue
    private Long id;

    private String token;
    private LocalDateTime expirationTime;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "voter_id", unique = true)
    private Voter voter;
}
