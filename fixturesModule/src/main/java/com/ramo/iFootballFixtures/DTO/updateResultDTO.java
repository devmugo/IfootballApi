package com.ramo.iFootballFixtures.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class updateResultDTO {

    private String teamName ;
    private String homeoraway ;
    private Long fixtureId;
    private Long tournamentId ;
    private String goalscorer;
    private Integer minutescored ;
    private String assistprovider ;
}
