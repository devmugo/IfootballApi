package com.ramo.iFootballFixtures.Models;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class goalStats {

    private String goalscorer;
    private Integer minuteScored;
    private String assistProvider ;
}


