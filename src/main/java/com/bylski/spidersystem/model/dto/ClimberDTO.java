package com.bylski.spidersystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClimberDTO {
    private String cardNumber;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}