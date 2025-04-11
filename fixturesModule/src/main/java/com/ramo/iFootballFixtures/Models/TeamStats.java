package com.ramo.iFootballFixtures.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
public class TeamStats {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "tournament_id", nullable = false)
        private Tournament tournament;

        @Column(nullable = false)
        private String teamName;
        private int matchesPlayed;
        private int gamesWon;
        private int gamesLost;
        private int gamesDrawn;
        private int goalsFor;
        private int goalsAgainst;
        private int points;



}
