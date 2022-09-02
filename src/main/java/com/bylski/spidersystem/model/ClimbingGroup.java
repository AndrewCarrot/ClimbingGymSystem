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
    @Enumerated(EnumType.STRING)
    private ClimbingGroupType type;
    // new code
    private LocalTime duration;

    //
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;
    private LocalTime classTime;

    public ClimbingGroup(Long id, String name, String coach, ClimbingGroupType type, DayOfWeek dayOfWeek, LocalTime classTime) {
        this.id = id;
        this.name = name;
        this.coach = coach;
        this.type = type;
        this.dayOfWeek = dayOfWeek;
        this.classTime = classTime;
        this.duration = type.getDuration();
    }


    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "group_climbers",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "climber_id")
    )
    @EqualsAndHashCode.Exclude
    private Set<Climber> climbers = new HashSet<>();

    public void addClimber(Climber climber){
        climbers.add(climber);
        climber.getClimbingGroups().add(this);
    }

    public void removeClimber(Climber climber){
        climbers.remove(climber);
        climber.getClimbingGroups().remove(this);
    }

}
