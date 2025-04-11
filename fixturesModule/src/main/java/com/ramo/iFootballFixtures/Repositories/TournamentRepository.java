package com.ramo.iFootballFixtures.Repositories;

import com.ramo.iFootballFixtures.Models.Fixture;
import com.ramo.iFootballFixtures.Models.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    List<Tournament> findTournamentsByFixturesIn(List<Fixture> fixtures);
    List<Tournament> findByAddedBy(String addedBy);

    @Query("SELECT t FROM Tournament t JOIN t.teams team WHERE team = :teamName")
    List<Tournament> findByTeamParticipating(@Param("teamName") String teamName);
}
