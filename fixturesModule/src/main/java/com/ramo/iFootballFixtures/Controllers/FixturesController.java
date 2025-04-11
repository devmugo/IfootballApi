package com.ramo.iFootballFixtures.Controllers;

import com.ramo.iFootballFixtures.DTO.TournamentFixturesDTO;
import com.ramo.iFootballFixtures.DTO.fixtureDTO;
import com.ramo.iFootballFixtures.DTO.updateResultDTO;
import com.ramo.iFootballFixtures.Models.Fixture;
import com.ramo.iFootballFixtures.Models.Tournament;
import com.ramo.iFootballFixtures.Services.fixtureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/fixtures")
@RequiredArgsConstructor
public class FixturesController {

    private final fixtureService fixtureservice;
    private static final Logger logger = LoggerFactory.getLogger(FixturesController.class);

    @PostMapping
    public ResponseEntity<Fixture> createFixture(@RequestBody fixtureDTO fixture) {
        try {
            ;
            return ResponseEntity.ok(fixtureservice.createFixture(fixture));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getFixtureById(@PathVariable Long id) {
        Optional<Fixture> fixture = fixtureservice.getFixtureById(id);

        if (fixture.isPresent()) {
            return ResponseEntity.ok(fixture.get()); // Returns Fixture object
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fixture not found."); // Returns error message
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFixture(@PathVariable Long id, @RequestBody Fixture updatedFixture) {
        Optional<Fixture> existingFixture = fixtureservice.getFixtureById(id);

        if (existingFixture.isPresent()) {
            updatedFixture.setFixtureId(id); // Ensure the correct ID is used
            Fixture savedFixture = fixtureservice.saveFixture(updatedFixture);
            return ResponseEntity.ok(savedFixture);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fixture not found.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFixture(@PathVariable Long id) {
        boolean deleted = fixtureservice.deleteFixture(id);
        if (deleted) {
            return ResponseEntity.ok("Fixture deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fixture not found.");
        }
    }
    @GetMapping("/group-by-date")
    public List<TournamentFixturesDTO> getFixturesByDate(@RequestParam String teamName) {
        return fixtureservice.getFixturesGroupedByDateForTournamentsByTeam(teamName);
    }

    @GetMapping("/live/{tournamentId}")
    public ResponseEntity<List<Fixture>> getLiveFixtures(@PathVariable Long tournamentId) {
        List<Fixture> liveFixtures = fixtureservice.getLiveFixturesForTournament(tournamentId);
        return ResponseEntity.ok(liveFixtures);
    }

    @PutMapping("/updateresults")
    public ResponseEntity<?> updateFixtureResult(@RequestBody updateResultDTO updateResultDTO) {
        try {
            Fixture updatedFixture = fixtureservice.updateResult(updateResultDTO);
            return ResponseEntity.ok(updatedFixture);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Unexpected error occurred"));
        }
    }


}
