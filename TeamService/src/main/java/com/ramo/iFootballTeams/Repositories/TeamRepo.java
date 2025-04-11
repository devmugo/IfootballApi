package com.ramo.iFootballTeams.Repositories;

import com.ramo.iFootballTeams.Models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface TeamRepo extends JpaRepository<Team, Long> {

    List<Team> findAllBydeletedFalse();

    List<Team> findAllByActiveFalse();

   Optional<List<Team>> findByNameContainingIgnoreCase(String searchTerm);

    Optional<Team> findByUniquename(String uniquename);
}
