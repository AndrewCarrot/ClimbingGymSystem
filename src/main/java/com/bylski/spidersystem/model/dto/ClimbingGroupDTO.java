package com.bylski.spidersystem.model.dto;

import com.bylski.spidersystem.model.ClimbingGroupType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClimbingGroupDTO{
    private String name;
    private String coach;
    private ClimbingGroupType type;
    private DayOfWeek dayOfWeek;
    private LocalTime classTime;
}