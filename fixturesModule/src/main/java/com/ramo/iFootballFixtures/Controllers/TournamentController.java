package com.ramo.iFootballFixtures.Controllers;

import com.ramo.iFootballFixtures.DTO.tournaReqDTO;
import com.ramo.iFootballFixtures.DTO.tournamentDetailsDTO;
import com.ramo.iFootballFixtures.Models.Fixture;
import com.ramo.iFootballFixtures.Models.Tournament;
import com.ramo.iFootballFixtures.Services.TournamentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/fixtures/tournaments")
public class TournamentController {

    private final TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }


    @PostMapping
    public ResponseEntity<Tournament> createTournament(@RequestBody tournaReqDTO tourna ) {
        try {
            Tournament savedTournament = tournamentService.createTournament(tourna);
            return ResponseEntity.ok(savedTournament);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping
    public ResponseEntity<List<Tournament>> getAllTournaments() {
        List<Tournament> tournaments = tournamentService.getAllTournaments();
        return ResponseEntity.ok(tournaments);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Tournament> getTournamentById(@PathVariable Long id) {
        Optional<Tournament> tournament = tournamentService.getTournamentById(id);
        if (tournament.isPresent()) {
            return ResponseEntity.ok(tournament.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Returns error message
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Tournament> updateTournament(@PathVariable Long id, @RequestBody Tournament updatedTournament) {
        Optional<Tournament> existingTournament = tournamentService.getTournamentById(id);

        if (existingTournament.isPresent()) {
            updatedTournament.setId(id); // Ensure the correct ID is used
            Tournament savedTournament = tournamentService.saveTournament(updatedTournament);
            return ResponseEntity.ok(savedTournament);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PutMapping("/addteam")
    public ResponseEntity<Tournament> addTeam(@RequestParam Long id, @RequestParam String team ) {
        Tournament updateTournament = tournamentService.addTeamToTournament(id , team );
        return ResponseEntity.ok(updateTournament);

    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTournament(@PathVariable Long id) {
        boolean deleted = tournamentService.deleteTournament(id);
        if (deleted) {
            return ResponseEntity.ok("Tournament deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tournament not found.");
        }
    }


    @GetMapping("/search")
    public ResponseEntity<List<Tournament>> searchTournamentsByTeams(@RequestParam String teamName) {
        List<Tournament> tournaments = tournamentService.findTournamentsByTeamName(teamName);
        if (tournaments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(tournaments);
    }
    @GetMapping("/added-by/{addedBy}")
    public ResponseEntity<List<Tournament>> getTournamentsByAddedBy(@PathVariable String addedBy) {
        List<Tournament> tournaments = tournamentService.getTournamentsByAddedBy(addedBy);
        if (tournaments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tournaments);
    }

    @GetMapping("/search-by-team")
    public List<Tournament> getTournamentsByTeam(@RequestParam String teamName) {
        return tournamentService.searchTournamentsByTeam(teamName);
    }
    @GetMapping("/dto/{addedBy}")
    public ResponseEntity<List<tournamentDetailsDTO>> getTournamentDTOByAddedBy(@PathVariable String addedBy) {
        List<tournamentDetailsDTO> tournaments = tournamentService.getTournamentDTOByAddedBy(addedBy);
        if (tournaments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tournaments);
    }


}

