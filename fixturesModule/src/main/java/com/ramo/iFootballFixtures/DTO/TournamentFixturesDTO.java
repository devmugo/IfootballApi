package com.ramo.iFootballFixtures.DTO;


import com.ramo.iFootballFixtures.Models.Fixture;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TournamentFixturesDTO {
    private String tournamentName;
    private Map<String, List<Fixture>> fixturesByDate;
}

