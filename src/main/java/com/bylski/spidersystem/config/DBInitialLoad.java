package com.bylski.spidersystem.config;

import com.bylski.spidersystem.model.*;
import com.bylski.spidersystem.repository.ClimberRepository;
import com.bylski.spidersystem.repository.ClimbingGroupRepository;
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
            ClimbingGroupRepository climbingGroupRepository
    ){
        return (args) -> {
            TimePass timePass = new TimePass(true,null, PassDuration.ONE_MONTH, LocalDate.now());
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
            ClimbingGroup climbingGroup = new ClimbingGroup(
                    1L,
                    "Z1",
                    "Karol",
                    ClimbingGroupType.ADVANCED,
                    DayOfWeek.MONDAY,
                    LocalTime.of(19,0)
            );

            climber1.setTimePass(timePass);
            climberRepository.save(climber1);
            climberRepository.save(climber2);
            climbingGroupRepository.save(climbingGroup);
        };
    }
}

