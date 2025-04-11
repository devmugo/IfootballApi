package com.ramo.iFootballFixtures.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class TournamentStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;
    @Column(nullable = false)
    private long tournamentId;
    private String topScorer;
    private String topAssists;
}
