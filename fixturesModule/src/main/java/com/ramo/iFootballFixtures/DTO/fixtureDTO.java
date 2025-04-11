package com.ramo.iFootballFixtures.DTO;

import com.ramo.iFootballFixtures.Enums.MatchStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class fixtureDTO {

    private String matchdate;
    private String matchtime;
    private Long tournamentId;
    private String venue;
    private MatchStatus status;
    private String hometeamname;
    private String awayteamname;


}
