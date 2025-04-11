package com.ramo.iFootballFixtures.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class tournamentDetailsDTO {
    private String tournamentName;
    private Long tournamentId;
}
