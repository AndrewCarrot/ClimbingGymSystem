package com.bylski.spidersystem.model;

import lombok.*;
import org.hibernate.annotations.NaturalId;

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
public class CustomPass extends Pass{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    private String cardNumber;

    private Integer punches;

    private String firstName;

    private String lastName;

}
