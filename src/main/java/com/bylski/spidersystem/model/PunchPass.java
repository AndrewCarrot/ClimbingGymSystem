package com.bylski.spidersystem.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@EqualsAndHashCode(callSuper = true)
public class PunchPass extends Pass{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer punches;

    public PunchPass(Integer punches, boolean discount, String note){
        super(discount, note);
        this.punches = punches;
    }

}
