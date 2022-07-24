package com.bylski.spidersystem.service.impl;

import com.bylski.spidersystem.model.Climber;
import com.bylski.spidersystem.model.ClimberAndCustomPass;
import com.bylski.spidersystem.model.CustomPass;
import com.bylski.spidersystem.repository.ClimberRepository;
import com.bylski.spidersystem.repository.CustomPassRepository;
import com.bylski.spidersystem.service.inf.ClimberAndCustomPassService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClimberAndCustomPassServiceImpl implements ClimberAndCustomPassService {

    private final ClimberRepository climberRepository;
    private final CustomPassRepository customPassRepository;

    public ClimberAndCustomPassServiceImpl(ClimberRepository climberRepository, CustomPassRepository customPassRepository) {
        this.climberRepository = climberRepository;
        this.customPassRepository = customPassRepository;
    }

    @Override
    public ClimberAndCustomPass getEntityByCardNumber(String cardNumber) {
        Optional<Climber> climber = climberRepository.findClimberByCardNumber(cardNumber);
        Optional<CustomPass> customPass = customPassRepository.findPassByCardNumber(cardNumber);

        ClimberAndCustomPass entities = new ClimberAndCustomPass();
        if (climber.isPresent()) {
            entities.setClimber(climber.get());
        } else {
            entities.setClimber(null);
        }
        if (customPass.isPresent()){
            entities.setCustomPass(customPass.get());
        } else {
            entities.setCustomPass(null);
        }

        return entities;
    }
}
