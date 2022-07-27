package com.bylski.spidersystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Climber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    private String cardNumber;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String phoneNumber;

    @Lob
    private String note;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "punchPassId")
    @EqualsAndHashCode.Exclude
    private PunchPass punchPass;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "timePassId")
    @EqualsAndHashCode.Exclude
    private TimePass timePass;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "classPassId")
    @EqualsAndHashCode.Exclude
    private ClassPass classPass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "climbingGroupId")
    @EqualsAndHashCode.Exclude
    private ClimbingGroup climbingGroup;

}
