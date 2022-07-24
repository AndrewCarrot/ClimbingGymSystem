package com.bylski.spidersystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClimberAndCustomPass {
    private Climber climber;
    private CustomPass customPass;
}
