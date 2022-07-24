package com.bylski.spidersystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TimePassDTO{
    private boolean discount;
    @Lob
    private String note;
    private Integer duration;
    private LocalDate validFrom;
}