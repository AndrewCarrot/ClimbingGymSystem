package com.bylski.spidersystem.controller;

import com.bylski.spidersystem.model.ClimbingGroup;
import com.bylski.spidersystem.model.ClimbingGroupType;
import com.bylski.spidersystem.model.dto.ClimbingGroupDTO;
import com.bylski.spidersystem.service.inf.ClimbingGroupService;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;

@RestController
@RequestMapping("/groups")
public class ClimbingGroupController {

    private final ClimbingGroupService climbingGroupService;

    public ClimbingGroupController(ClimbingGroupService climbingGroupService) {
        this.climbingGroupService = climbingGroupService;
    }

    @PostMapping("/new")
    public void createGroup(@RequestBody ClimbingGroupDTO climbingGroupDTO){
        climbingGroupService.createGroup(climbingGroupDTO);
    }

    @GetMapping("/get/by-name")
    public ClimbingGroup getGroupByName(@RequestParam String name){
        return climbingGroupService.getGroupByName(name);
    }

    @GetMapping("/get/by-day")
    public List<ClimbingGroup> getGroupsByDayOfWeek(@RequestParam DayOfWeek dayOfWeek){
        return climbingGroupService.getGroupsByDayOfWeek(dayOfWeek);
    }

    @GetMapping("/get/by-coach")
    public List<ClimbingGroup> getGroupsByCoach(@RequestParam String coachName){
        return climbingGroupService.getGroupsByCoach(coachName);
    }

    @GetMapping("/get/by-type")
    public List<ClimbingGroup> getGroupsByType(@RequestParam ClimbingGroupType type){
        return climbingGroupService.getGroupsByType(type);
    }

    @GetMapping("/get/all")
    public List<ClimbingGroup> getAllGroups(){
        return climbingGroupService.getAllGroups();
    }

    @PutMapping("/update")
    public void updateClimbingGroup(@RequestParam Long groupId, @RequestBody ClimbingGroupDTO climbingGroupDTO){
        climbingGroupService.updateGroup(groupId, climbingGroupDTO);
    }

    @PatchMapping("/add-climber")
    public void addClimberToGroup(@RequestParam Long climberId, @RequestParam Long groupId){
        climbingGroupService.addClimberToGroup(climberId, groupId);
    }

    @PatchMapping("/delete-climber")
    public void deleteClimberFromGroup(@RequestParam Long climberId, @RequestParam Long groupId){
        climbingGroupService.deleteClimberFromGroup(climberId, groupId);
    }

    @DeleteMapping("/delete")
    public void deleteGroup(@RequestParam Long groupId){
        climbingGroupService.deleteGroup(groupId);
    }

}
