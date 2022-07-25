package com.bylski.spidersystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClimbingGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String coach;
    private ClimbingGroupType type;
    private DayOfWeek dayOfWeek;
    private LocalTime classTime;


    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "climbingGroup")
    @EqualsAndHashCode.Exclude
    private Set<Climber> climbers = new HashSet<>();

    public void addClimber(Climber climber){
        climbers.add(climber);
        climber.setClimbingGroup(this);
    }

    public void removeClimber(Climber climber){
        climbers.remove(climber);
        climber.setClimbingGroup(null);
    }

}
