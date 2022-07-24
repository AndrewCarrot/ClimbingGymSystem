package com.bylski.spidersystem.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@EqualsAndHashCode(callSuper = true)
public class TimePass extends Pass{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer duration;

    private LocalDate validFrom;

    private LocalDate validTill;


    public TimePass(boolean discount, String note,Integer duration, LocalDate validFrom){
        super(discount,note);
        this.duration = duration;
        this.validFrom = validFrom;
        this.validTill = validFrom.plusDays(duration);
        this.setType(PassType.TIME);
    }
}
