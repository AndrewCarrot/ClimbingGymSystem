package com.bylski.spidersystem.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter @Setter
@NoArgsConstructor
public abstract class Pass {

    private boolean discount;

    @Lob
    private String note; // TODO missing methods for adding note

    public Pass(boolean discount, String note){
        this.discount = discount;
        this.note = note;
    }

}
