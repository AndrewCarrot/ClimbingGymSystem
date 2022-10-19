package com.bylski.spidersystem.config;

import com.bylski.spidersystem.model.*;
import com.bylski.spidersystem.repository.ClimberRepository;
import com.bylski.spidersystem.repository.ClimbingGroupRepository;
import com.bylski.spidersystem.repository.CustomPassRepository;
import com.bylski.spidersystem.service.inf.TimePassService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Configuration
public class DBInitialLoad {
    @Bean
    public CommandLineRunner loadData(
            ClimberRepository climberRepository,
            ClimbingGroupRepository climbingGroupRepository,
            CustomPassRepository customPassRepository
    ){
        return (args) -> {
            TimePass timePass = new TimePass(true,null, PassDuration.ONE_MONTH, LocalDate.now());
            ClassPass classPass = new ClassPass(true,"notatka",ClassFrequency.TWICE_PER_WEEK,LocalDate.now(),false);
            PunchPass punchPass = new PunchPass(8,true,"kolejna");
            Climber climber1 = new Climber(1L,
                    "123456789",
                    "Marcin",
                    "Bylski",
                    "692738635",
                    null,
                    null,
                    null,
                    null,
                    null);
            Climber climber2 = new Climber(2L,
                    "987654321",
                    "Krzysztof",
                    "Bąk",
                    "474938373",
                    "trochę capi",
                    null,
                    null,
                    null,
                    null);
            ClimbingGroup climbingGroup1 = new ClimbingGroup(
                    1L,
                    "Z1",
                    "Karol",
                    ClimbingGroupType.ADVANCED,
                    DayOfWeek.MONDAY,
                    LocalTime.of(19,0)
            );
            ClimbingGroup climbingGroup2 = new ClimbingGroup(
                    2L,
                    "D1",
                    "Mikołaj",
                    ClimbingGroupType.CHILDREN,
                    DayOfWeek.MONDAY,
                    LocalTime.of(19,0)
            );
            ClimbingGroup climbingGroup3 = new ClimbingGroup(
                    3L,
                    "S1",
                    "Mikołaj",
                    ClimbingGroupType.INTERMEDIATE,
                    DayOfWeek.MONDAY,
                    LocalTime.of(17,0)
            );
            ClimbingGroup climbingGroup4 = new ClimbingGroup(
                    4L,
                    "P1",
                    "Mikołaj",
                    ClimbingGroupType.BEGINNERS,
                    DayOfWeek.TUESDAY,
                    LocalTime.of(16,30)
            );


            CustomPass customPass1 = new CustomPass(
                    1L,
                    "8679584738",
                    8,
                    "Ryszard",
                    "Świdnik"
            );
            customPass1.setDiscount(false);
            customPass1.setNote("karnet prezentowy");
            customPassRepository.save(customPass1);

            climber1.setTimePass(timePass);
            climber1.setPunchPass(punchPass);
            climber1.setClassPass(classPass);
            climberRepository.save(climber1);
            climberRepository.save(climber2);
            climbingGroupRepository.save(climbingGroup1);
            climbingGroupRepository.save(climbingGroup2);
            climbingGroupRepository.save(climbingGroup3);
            climbingGroupRepository.save(climbingGroup4);
        };
    }
}

