package com.ramo.iFootballTeams.Services;

import com.ramo.iFootballTeams.DTO.TeamDTO;
import com.ramo.iFootballTeams.Models.Team;
import com.ramo.iFootballTeams.Repositories.TeamRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamService {

    @Autowired
    private TeamRepo teamRepo;

    public void createTeam(TeamDTO team) {
        Team teamEntity = Team.builder()
                .name(team.getName())
                .location(team.getLocation())
                .stadium(team.getStadium())
                .active(Boolean.FALSE)
                .short_name(team.getShort_name())
                .logo_url(team.getLogo_url())
                .founded_year(team.getFounded_year())
                .deleted(Boolean.FALSE)
                .build();
        teamRepo.save(teamEntity);


    }

    public Team getTeamById(Long id) {
        return teamRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Team with id " + id + " not found"));
    }

    public void approveTeam(Long id) {
        Team team = teamRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Team with id " + id + " not found"));
        team.setActive(true);
        teamRepo.save(team);
    }
    public void deleteTeam(Long id){
        Team team = teamRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Team with id " + id + " not found"));
        team.setDeleted(true);
        teamRepo.save(team);
    }
    public void restoreTeam(Long id){
        Team team = teamRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Team with id " + id + " not found"));
        team.setDeleted(false);
    }

    public List<Team> getAllActiveTeams() {
        return teamRepo.findAllBydeletedFalse();
    }

    public List<Team> getAllUnapprovedTeams() {
        return teamRepo.findAllByActiveFalse();
    }

    public void updateTeam(TeamDTO team, Long Id ) {
        Team existingTeam = teamRepo.findById(Id)
                .orElseThrow(() -> new RuntimeException("Team with id " + Id + " not found"));
        existingTeam.setName(team.getName());
        existingTeam.setShort_name(team.getShort_name());
        existingTeam.setFounded_year(team.getFounded_year());
        existingTeam.setStadium(team.getStadium());
        existingTeam.setLocation(team.getLocation());
        existingTeam.setLogo_url(team.getLogo_url());
        existingTeam.setUniquename(team.getName()+team.getLocation());
        teamRepo.save(existingTeam);


    }

    public Optional<List<Team>> getTeamsByName(String searchTerm) {
        return teamRepo.findByNameContainingIgnoreCase(searchTerm);
    }

    public Optional<Team> getTeamByUniqueName( String uniquename) {
        return teamRepo.findByUniquename(uniquename);
    }
}
