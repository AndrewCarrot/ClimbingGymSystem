package com.bylski.spidersystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
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

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "punchPassId")
    private PunchPass punchPass;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "timePassId")
    private TimePass timePass;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "classPassId")
    private ClassPass classPass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "climbingGroupId", nullable = false)
    private ClimbingGroup climbingGroup;

}
