package com.bylski.spidersystem.repository;

import com.bylski.spidersystem.model.ClimbingGroup;
import com.bylski.spidersystem.model.ClimbingGroupType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClimbingGroupRepository extends JpaRepository<ClimbingGroup,Long> {
    Optional<ClimbingGroup> findClimbingGroupByName(String name);
    List<ClimbingGroup> getClimbingGroupsByDayOfWeek(DayOfWeek dayOfWeek);
    List<ClimbingGroup> getClimbingGroupsByCoach(String coach);
    List<ClimbingGroup> getClimbingGroupsByType(ClimbingGroupType type);
}
