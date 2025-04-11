package com.ramo.iFootballFixtures.Repositories;

import com.ramo.iFootballFixtures.Enums.MatchStatus;
import com.ramo.iFootballFixtures.Models.Fixture;
import com.ramo.iFootballFixtures.Models.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface fixtureRepo extends JpaRepository<Fixture , Long> {

    List<Fixture> findByHomeTeamContainingOrAwayTeamContaining(String homeTeam, String awayTeam);

    List<Fixture> findByTournament(Tournament tournament);

    List<Fixture> findByMatchStatusAndMatchTimestampLessThanEqual(MatchStatus status, LocalDateTime timestamp);




    List<Fixture> findByMatchStatusAndTournamentId(MatchStatus matchStatus, Long tournamentId);
}
