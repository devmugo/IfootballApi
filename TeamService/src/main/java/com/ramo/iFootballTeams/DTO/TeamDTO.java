package com.ramo.iFootballTeams.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {
        private String name;
        private String short_name;
        private int founded_year;
        private String stadium;
        private String location;
        private String logo_url;

    }
