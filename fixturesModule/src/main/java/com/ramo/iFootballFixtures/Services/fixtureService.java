package com.ramo.iFootballFixtures.Services;

import com.ramo.iFootballFixtures.DTO.TournamentFixturesDTO;
import com.ramo.iFootballFixtures.DTO.fixtureDTO;
import com.ramo.iFootballFixtures.DTO.updateResultDTO;
import com.ramo.iFootballFixtures.Enums.MatchStatus;
import com.ramo.iFootballFixtures.Models.Fixture;
import com.ramo.iFootballFixtures.Models.Result;
import com.ramo.iFootballFixtures.Models.Tournament;
import com.ramo.iFootballFixtures.Models.goalStats;
import com.ramo.iFootballFixtures.Repositories.TournamentRepository;
import com.ramo.iFootballFixtures.Repositories.fixtureRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@NoArgsConstructor
@AllArgsConstructor
public class fixtureService {

    @Autowired
    private fixtureRepo fixturerepo;

    @Autowired
    private TournamentRepository tournarepo;

    @Autowired
    private TournamentService tournaservice;





    public Fixture createFixture(fixtureDTO fixture) {
        Optional<Tournament> tournamentOpt = tournarepo.findById(fixture.getTournamentId());

        if (tournamentOpt.isPresent()) {
            Tournament tournament = tournamentOpt.get();

            Fixture newFixture = Fixture.builder()
                    .matchDate(fixture.getMatchdate())
                    .matchtime(fixture.getMatchtime())
                    .venue(fixture.getVenue())
                    .matchStatus(MatchStatus.SCHEDULED)
                    .homeTeam(fixture.getHometeamname())
                    .awayTeam(fixture.getAwayteamname())
                    .tournament(tournament)
                    .matchTimestamp(getMatchDateTime(fixture.getMatchdate() , fixture.getMatchtime()))
                    .build();
            fixturerepo.save(newFixture);

            tournament.getFixtures().add(newFixture);
            tournarepo.save(tournament);
            return newFixture;
        }else {
            throw new RuntimeException("Tournament not found with ID: " + fixture.getTournamentId());
        }
    }

    public Optional<Fixture> getFixtureById(Long id) {
        return fixturerepo.findById(id);
    }

    public Fixture saveFixture(Fixture fixture) {
        return fixturerepo.save(fixture);
    }

    public boolean deleteFixture(Long id) {
        if (fixturerepo.existsById(id)) {
            fixturerepo.deleteById(id);
            return true;
        }
        return false;
    }


    public List<TournamentFixturesDTO> getFixturesGroupedByDateForTournamentsByTeam(String teamName) {
        List<Tournament> allTournaments = tournarepo.findAll();
        String lowerSearchTerm = teamName.toLowerCase();

        return allTournaments.stream()
                .filter(t -> t.getTeams().stream().anyMatch(team -> team.toLowerCase().contains(lowerSearchTerm)))
                .map(tournament -> {
                    Map<String, List<Fixture>> groupedFixtures = tournament.getFixtures().stream()
                            .collect(Collectors.groupingBy(Fixture::getMatchDate, TreeMap::new, Collectors.toList()));

                    return new TournamentFixturesDTO(tournament.getName(), groupedFixtures);
                })
                .collect(Collectors.toList());
    }

    public LocalDateTime getMatchDateTime(String matchDate, String matchTime) {
        String dateTimeString = matchDate + "T" + matchTime; // Example: "2025-04-04T15:30"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return LocalDateTime.parse(dateTimeString, formatter);
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void updateLiveMatchStatuses() {
        LocalDateTime now = LocalDateTime.now();
        List<Fixture> fixturesToUpdate = fixturerepo
                .findByMatchStatusAndMatchTimestampLessThanEqual(MatchStatus.SCHEDULED, now);

        for (Fixture fixture : fixturesToUpdate) {
            fixture.setMatchStatus(MatchStatus.LIVE);
        }

        if (!fixturesToUpdate.isEmpty()) {
            fixturerepo.saveAll(fixturesToUpdate);
        }
    }

    public List<Fixture> getLiveFixturesForTournament(Long tournamentId) {
        return fixturerepo.findByMatchStatusAndTournamentId(MatchStatus.LIVE, tournamentId);
    }

    @Transactional
    public Fixture updateResult(updateResultDTO results) {
        Fixture fixture = fixturerepo.findById(results.getFixtureId())
                .orElseThrow(() -> new RuntimeException("Fixture not found"));

        if (fixture.getTournament() == null || !fixture.getTournament().getId().equals(results.getTournamentId())) {
            throw new RuntimeException("Fixture does not belong to the specified tournament");
        }


        goalStats goal = new goalStats();
        goal.setGoalscorer(results.getGoalscorer());
        goal.setAssistProvider(results.getAssistprovider());
        goal.setMinuteScored(results.getMinutescored());

        Result result = fixture.getResults();

        if (result == null) {
            result = new Result();
            if ("home".equalsIgnoreCase(results.getHomeoraway())) {
                List<goalStats> homeGoals = new ArrayList<>();
                homeGoals.add(goal);
                result.setHomeTeamGoalsStats(homeGoals);
                result.setHomeTeamGoals(result.getHomeTeamGoalsStats().size());
            } else if ("away".equalsIgnoreCase(results.getHomeoraway())) {
                List<goalStats> awayGoals = new ArrayList<>();
                awayGoals.add(goal);
                result.setAwayTeamGoalsStats(awayGoals);
                result.setAwayTeamGoals(result.getAwayTeamGoalsStats().size());
            } else {
                throw new IllegalArgumentException("Invalid team side: " + results.getHomeoraway());
            }

            fixture.setResults(result);
        } else
        {
            if ("home".equalsIgnoreCase(results.getHomeoraway())) {
                if (result.getHomeTeamGoalsStats() == null) {
                    result.setHomeTeamGoalsStats(new ArrayList<>());
                }
                result.getHomeTeamGoalsStats().add(goal);
            } else if ("away".equalsIgnoreCase(results.getHomeoraway())) {
                if (result.getAwayTeamGoalsStats() == null) {
                    result.setAwayTeamGoalsStats(new ArrayList<>());
                }
                result.getAwayTeamGoalsStats().add(goal);
            } else {
                throw new IllegalArgumentException("Invalid team side: " + results.getHomeoraway());
            }
        }

        fixturerepo.save(fixture);
        fixturerepo.flush();
        return fixture;
    }




}
