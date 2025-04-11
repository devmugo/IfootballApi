package com.ramo.iFootballFixtures.Services;

import com.ramo.iFootballFixtures.DTO.tournaReqDTO;
import com.ramo.iFootballFixtures.DTO.tournamentDetailsDTO;
import com.ramo.iFootballFixtures.Models.Fixture;
import com.ramo.iFootballFixtures.Models.Tournament;
import com.ramo.iFootballFixtures.Repositories.TournamentRepository;
import com.ramo.iFootballFixtures.Repositories.fixtureRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

@Service
public class TournamentService {

    private final TournamentRepository tournamentRepository;
    private final fixtureRepo fixtureRepository;



    // ✅ Save or update a tournament
    public Tournament saveTournament(Tournament tournament) {
        return tournamentRepository.save(tournament);
    }
    public Tournament createTournament(tournaReqDTO tourna ) {
        Tournament tournament = new Tournament();
        tournament.setName(tourna.getName());
        tournament.setAddedBy(tourna.getCreatedBy());
        return tournamentRepository.save(tournament);
    }

    // ✅ Get all tournaments
    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    // ✅ Get tournament by ID
    public Optional<Tournament> getTournamentById(Long id) {
        return tournamentRepository.findById(id);
    }

    // ✅ Delete a tournament
    public boolean deleteTournament(Long id) {
        if (tournamentRepository.existsById(id)) {
            tournamentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // ✅ Find tournaments by team name in their fixtures
    public List<Tournament> findTournamentsByTeamName(String teamName) {
        List<Fixture> fixtures = fixtureRepository.findByHomeTeamContainingOrAwayTeamContaining(teamName, teamName);
        return tournamentRepository.findTournamentsByFixturesIn(fixtures);
    }

    public List<Tournament> getTournamentsByAddedBy(String addedBy) {
        return tournamentRepository.findByAddedBy(addedBy);
    }

    public Tournament addTeamToTournament(Long id, String team) {
        return tournamentRepository.findById(id).map(existingTournament -> {
            List<String> teamsList = existingTournament.getTeams();
            if (!teamsList.contains(team)) {
                teamsList.add(team);
            }
            existingTournament.setTeams(teamsList);
            return tournamentRepository.save(existingTournament);
        }).orElseThrow(() -> new EntityNotFoundException("Tournament not found with id " + id));
    }

    public List<Tournament> searchTournamentsByTeam(String teamName) {
        return tournamentRepository.findByTeamParticipating(teamName);
    }

    public List<tournamentDetailsDTO> getTournamentDTOByAddedBy(String addedBy) {
        List<Tournament> tournaments =  tournamentRepository.findByAddedBy(addedBy);
        tournamentDetailsDTO tournadto = new tournamentDetailsDTO();
        List<tournamentDetailsDTO> tournamentsdto = new ArrayList<>();

        for (Tournament tournament : tournaments){
            tournadto.setTournamentId(tournament.getId());
            tournadto.setTournamentName(tournament.getName());
            tournamentsdto.add(tournadto);

        }
        return  tournamentsdto;


    }



}

