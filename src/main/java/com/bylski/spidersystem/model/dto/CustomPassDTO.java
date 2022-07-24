package com.bylski.spidersystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomPassDTO{
    private String cardNumber;
    private Integer punches;
    private String firstName;
    private String lastName;
    private boolean discount;
    @Lob
    private String note;
}