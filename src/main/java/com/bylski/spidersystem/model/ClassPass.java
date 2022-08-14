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
public class ClassPass extends Pass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer punches;

    private LocalDate validFrom;

    private LocalDate validTill;

    private boolean multisport;

    @Enumerated(EnumType.STRING)
    private ClassFrequency classFrequency;

    public ClassPass(boolean discount,String note, ClassFrequency classFrequency, LocalDate validFrom, boolean multisport){
        super(discount,note);
        this.classFrequency = classFrequency;
        this.validFrom = validFrom;
        this.validTill = validFrom.plusDays(30);
        this.multisport = multisport;
        this.punches = classFrequency==ClassFrequency.ONCE_PER_WEEK ? 4:8;
    }

}
