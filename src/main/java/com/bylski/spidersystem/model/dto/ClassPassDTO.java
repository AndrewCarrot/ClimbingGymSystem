package com.bylski.spidersystem.model.dto;

import com.bylski.spidersystem.model.ClassFrequency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClassPassDTO{
    private boolean discount;
    @Lob
    private String note;
    private ClassFrequency classFrequency;
    private LocalDate validFrom;
    private boolean multisport;
}