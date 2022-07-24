package com.bylski.spidersystem.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter @Setter
@NoArgsConstructor
public abstract class Pass {

    @Enumerated(EnumType.STRING)
    private PassType type;

    private boolean discount;

    @Lob
    private String note;

    public Pass(boolean discount, String note){
        this.discount = discount;
        this.note = note;
    }

}
