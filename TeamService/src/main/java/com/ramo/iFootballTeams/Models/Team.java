package com.ramo.iFootballTeams.Models;

import jakarta.persistence.*;
import lombok.*;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity()
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long team_id;

    @Column(nullable = false, length = 255, unique = true)
    private String name;

    @Column(nullable = false, length = 50 , unique = true)
    private String short_name;

    @Column(nullable = false)
    private int founded_year;

    @Column(nullable = true, length = 255)
    private String stadium;

    @Column(nullable = true, length = 255)
    private String location;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String logo_url;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false)
    private Boolean deleted ;

    @Column(nullable = false, unique = true)
    private String uniquename;


}
