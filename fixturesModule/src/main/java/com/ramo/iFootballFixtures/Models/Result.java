package com.ramo.iFootballFixtures.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "results")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resultid;

    private Integer homeTeamGoals;
    private Integer awayTeamGoals;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "resultid", referencedColumnName = "resultid")
    private List<goalStats> homeTeamGoalsStats;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "resultid", referencedColumnName = "resultid")
    private List<goalStats> awayTeamGoalsStats;

    @OneToOne(mappedBy = "results", cascade = CascadeType.ALL)
    private Fixture fixture;

    public Integer getHomeTeamGoals() {
        return homeTeamGoalsStats != null ? homeTeamGoalsStats.size() : 0;
    }

    public Integer getAwayTeamGoals() {
        return awayTeamGoalsStats != null ? awayTeamGoalsStats.size() : 0;
    }

    // Optional setter methods to update the list of goalStats
    public void setHomeTeamGoalsStats(List<goalStats> homeGoals) {
        this.homeTeamGoalsStats = homeGoals;
        this.homeTeamGoals = homeGoals.size(); // Automatically update homeTeamGoals based on the list size
    }

    public void setAwayTeamGoalsStats(List<goalStats> awayGoals) {
        this.awayTeamGoalsStats = awayGoals;
        this.awayTeamGoals = awayGoals.size(); // Automatically update awayTeamGoals based on the list size
    }
}
