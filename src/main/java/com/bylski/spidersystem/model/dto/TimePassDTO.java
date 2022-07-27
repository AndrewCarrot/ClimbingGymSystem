package com.bylski.spidersystem.model.dto;

import com.bylski.spidersystem.model.PassDuration;
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
    private PassDuration duration;
    private LocalDate validFrom;
}