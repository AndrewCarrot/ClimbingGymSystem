package com.bylski.spidersystem.model;

import lombok.*;

import javax.persistence.*;
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

    @Enumerated(EnumType.STRING)
    private PassDuration duration;

    private LocalDate validFrom;

    private LocalDate validTill;


    public TimePass(boolean discount, String note,PassDuration duration, LocalDate validFrom){
        super(discount,note);
        this.duration = duration;
        this.validFrom = validFrom;
        this.validTill = duration == PassDuration.ONE_MONTH ? validFrom.plusDays(30):validFrom.plusDays(90);
    }
}
