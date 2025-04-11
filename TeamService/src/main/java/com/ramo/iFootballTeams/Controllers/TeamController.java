package com.ramo.iFootballTeams.Controllers;

import com.ramo.iFootballTeams.DTO.TeamDTO;
import com.ramo.iFootballTeams.Models.Team;
import com.ramo.iFootballTeams.Services.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("/api/v1/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @PostMapping("/create")
    public ResponseEntity<String> createTeam(@RequestBody TeamDTO team){
        teamService.createTeam(team);
        return ResponseEntity.ok("Team created");
    }
    @GetMapping("/view/{id}")
    public ResponseEntity<Team> viewTeam(@PathVariable Long id){
        return ResponseEntity.ok( teamService.getTeamById(id));
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<String> approveTeam(@PathVariable Long id){
        teamService.approveTeam(id);
        return ResponseEntity.ok("Team with id: "+id+" has been approved");
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteTeam(@PathVariable Long id){
        teamService.deleteTeam(id);
        return ResponseEntity.ok("Team with id: "+id+" is marked as deleted");
    }
    @PutMapping("/restore")
    public ResponseEntity<String> restoreTeam(Long Id){
        teamService.restoreTeam(Id);
        return ResponseEntity.ok("Team Successfully restored");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateTeam(@RequestBody TeamDTO team, @PathVariable Long id){
        teamService.updateTeam(team, id);
        return ResponseEntity.ok("Team Details Successfully Updated ");
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<Team>> getAllActiveTeams(){
        return ResponseEntity.ok(teamService.getAllActiveTeams());
    }
    @GetMapping("/unapproved")
    public ResponseEntity<Iterable<Team>> getAllUnapprovedTeams(){
        return ResponseEntity.ok(teamService.getAllUnapprovedTeams());
    }

    @GetMapping("/searchTeams")
    public ResponseEntity<Optional<List<Team>>> searchTeamsByName (@RequestParam String searchTerm){
        return ResponseEntity.ok(teamService.getTeamsByName(searchTerm));
    }
    @GetMapping("/searchTeam")
    public ResponseEntity<Optional<Team>> searchTeam(@RequestParam String searchTerm){
        return ResponseEntity.ok(teamService.getTeamByUniqueName(searchTerm));
    }


}
