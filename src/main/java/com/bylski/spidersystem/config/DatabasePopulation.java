package com.bylski.spidersystem.config;

import com.bylski.spidersystem.exception.ResourceNotFoundException;
import com.bylski.spidersystem.model.ClassFrequency;
import com.bylski.spidersystem.model.ClassPass;
import com.bylski.spidersystem.model.Climber;
import com.bylski.spidersystem.repository.ClassPassRepository;
import com.bylski.spidersystem.repository.ClimberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DatabasePopulation implements CommandLineRunner {

    private final ClimberRepository climberRepository;
    private final ClassPassRepository classPassRepository;

    public DatabasePopulation(ClimberRepository climberRepository, ClassPassRepository classPassRepository) {
        this.climberRepository = climberRepository;
        this.classPassRepository = classPassRepository;
    }

    @Override
    public void run(String... args) {

        Climber climber = new Climber(1L,"123","Maciej","Korzybski","692738635",null,null,null,null,null);
        climberRepository.save(climber);

        ClassPass classPass = new ClassPass(1L,8, LocalDate.now(),LocalDate.now().plusMonths(1),true, ClassFrequency.TWICE_PER_WEEK);

        climber.setClassPass(classPass);

        // class pass is persisted alongside climber
        climberRepository.save(climber);

        // delete class pass
//        Climber climber1 = climberRepository.findById(1L).orElse(null);
//        ClassPass classPass1 = climber1.getClassPass();
//
//        climber1.setClassPass(null);
//        climberRepository.save(climber1);
//        classPassRepository.delete(classPass1);


        //update class pass - take one puch
//        Climber climber1 = climberRepository.findById(1L).orElse(null);
//        ClassPass classPass1 = climber1.getClassPass();
//        classPass1.setPunches(classPass1.getPunches()-1);
//        classPassRepository.save(classPass1);





    }
}
