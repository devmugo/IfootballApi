package com.ramo.iFootballFixtures.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ramo.iFootballFixtures.Enums.MatchStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fixtures")
public class Fixture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fixtureId;

    @Column(nullable = false)
    private String matchDate;

    @Column(nullable = false)
    private String matchtime;

    private String venue;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MatchStatus matchStatus = MatchStatus.SCHEDULED;

    private Long hometeamid;

    private Long awayteamid;

    @Column(nullable = false)
    private String homeTeam;

    @Column(nullable = false)
    private String awayTeam;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "resultid", referencedColumnName = "resultid")
    private Result results;

    @ManyToOne
    @JoinColumn(name = "tournamentId", referencedColumnName = "id")
    @JsonBackReference
    private Tournament tournament;

    @Column(nullable = false)
    private LocalDateTime matchTimestamp;
}
