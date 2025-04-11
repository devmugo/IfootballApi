package com.ramo.iFootballFixtures.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String addedBy;

    // Tournament has a list of Fixtures, which are related via @OneToMany
    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Fixture> fixtures;

    @ElementCollection
    private List<TeamStats> tournamentTable;

    @ElementCollection
    @Column(name = "team")
    private List<String> teams;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "statsid", referencedColumnName = "id")
    private TournamentStats tournamentStats; // Overall tournament statistics
}
