package com.bylski.spidersystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Climber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId(mutable = true)
    @Column(unique = true)
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

    @JsonIgnore // TODO temporary, serialization issue, use DTOs
    @ManyToMany(mappedBy = "climbers")
    @EqualsAndHashCode.Exclude
    private Set<ClimbingGroup> climbingGroups = new HashSet<>();

}
