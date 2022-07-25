package com.bylski.spidersystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClimberOrCustomPass {
    private Climber climber;
    private CustomPass customPass;
}
