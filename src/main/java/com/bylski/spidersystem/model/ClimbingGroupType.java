package com.bylski.spidersystem.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

public enum ClimbingGroupType {
    CHILDREN(LocalTime.of(1,0)),
    BEGINNERS(LocalTime.of(2,0)),
    INTERMEDIATE(LocalTime.of(2,0)),
    ADVANCED(LocalTime.of(2,0));
    @Getter @Setter
    private LocalTime duration;
    ClimbingGroupType(LocalTime duration){
        this.duration = duration;
    }


}
