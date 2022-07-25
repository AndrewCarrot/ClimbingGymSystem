package com.bylski.spidersystem.service.inf;

import com.bylski.spidersystem.model.ClimbingGroup;
import com.bylski.spidersystem.model.ClimbingGroupType;
import com.bylski.spidersystem.model.dto.ClimbingGroupDTO;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

public interface ClimbingGroupService {
    void createGroup(ClimbingGroupDTO climbingGroupDTO);

    ClimbingGroup getGroupByName(String name);
    List<ClimbingGroup> getGroupsByDayOfWeek(DayOfWeek dayOfWeek);
    List<ClimbingGroup> getGroupsByCoach(String coach);
    List<ClimbingGroup> getGroupsByType(ClimbingGroupType type);
    List<ClimbingGroup> getAllGroups();

    void updateGroup(Long id, ClimbingGroupDTO climbingGroupDTO);
    void addClimberToGroup(Long climberId, Long climbingGroupId);
    void deleteClimberFromGroup(Long climberId, Long climbingGroupId);

    void deleteGroup(Long id);

}
