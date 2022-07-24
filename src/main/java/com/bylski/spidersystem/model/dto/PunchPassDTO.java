package com.bylski.spidersystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PunchPassDTO{
    private Integer punches;
    private boolean discount;
    @Lob
    private String note;
}