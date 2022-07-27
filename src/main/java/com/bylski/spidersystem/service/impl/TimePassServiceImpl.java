package com.bylski.spidersystem.service.impl;

import com.bylski.spidersystem.exception.ResourceNotFoundException;
import com.bylski.spidersystem.model.Climber;
import com.bylski.spidersystem.model.PassDuration;
import com.bylski.spidersystem.model.TimePass;
import com.bylski.spidersystem.model.dto.TimePassDTO;
import com.bylski.spidersystem.repository.ClimberRepository;
import com.bylski.spidersystem.repository.TimePassRepository;
import com.bylski.spidersystem.service.inf.TimePassService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TimePassServiceImpl implements TimePassService {

    private final TimePassRepository timePassRepository;
    private final ClimberRepository climberRepository;

    public TimePassServiceImpl(TimePassRepository timePassRepository, ClimberRepository climberRepository) {
        this.timePassRepository = timePassRepository;
        this.climberRepository = climberRepository;
    }

    @Override
    public void addTimePass(Long climberId, TimePassDTO timePassDTO) {
        Climber climber = climberRepository.findById(climberId)
                .orElseThrow(()->new ResourceNotFoundException("climber","id",climberId));

        TimePass timePass = new TimePass(
                timePassDTO.isDiscount(),
                timePassDTO.getNote(),
                timePassDTO.getDuration(),
                timePassDTO.getValidFrom()
        );

        climber.setTimePass(timePass);
        climberRepository.save(climber);

    }

    @Override
    public void deleteTimePass(Long climberId) {
        Climber climber = climberRepository.findById(climberId)
                .orElseThrow(()->new ResourceNotFoundException("climber","id",climberId));
        TimePass timePass = climber.getTimePass();

        climber.setTimePass(null);
        climberRepository.save(climber);
        timePassRepository.delete(timePass);
    }

    @Override
    public TimePass getPass(Long climberId) {
        return climberRepository.findById(climberId).map(Climber::getTimePass)
                .orElseThrow(()->new ResourceNotFoundException("climber","id",climberId));
    }

    @Override
    public void renewPass(Long climberId) {
        Climber climber = climberRepository.findById(climberId)
                .orElseThrow(() -> new ResourceNotFoundException("climber","id",climberId));
        TimePass timePass = climber.getTimePass();

        if(timePass != null) {
            timePass.setValidFrom(LocalDate.now());
            timePass.setValidTill(
                    timePass.getDuration() == PassDuration.ONE_MONTH ?
                            timePass.getValidFrom().plusDays(30) :
                            timePass.getValidFrom().plusDays(90)
            );
            timePassRepository.save(timePass);
        }

    }

    @Override
    public void updatePass(Long climberId, TimePassDTO timePassDTO) {
        Climber climber = climberRepository.findById(climberId)
                .orElseThrow(() -> new ResourceNotFoundException("climber","id",climberId));
        TimePass timePass = climber.getTimePass();

        if(timePass != null){
            timePass.setDiscount(timePassDTO.isDiscount());
            timePass.setNote(timePass.getNote());
            timePass.setDuration(timePassDTO.getDuration());
            timePass.setValidFrom(timePassDTO.getValidFrom());
            timePass.setValidTill(
                    timePassDTO.getDuration() == PassDuration.ONE_MONTH ?
                            timePassDTO.getValidFrom().plusDays(30) :
                            timePassDTO.getValidFrom().plusDays(90)
            );
            timePassRepository.save(timePass);
        }
    }

    @Override
    public void addDays(Long climberId, Integer days) {
        Climber climber = climberRepository.findById(climberId)
                .orElseThrow(() -> new ResourceNotFoundException("climber", "id",climberId));
        TimePass timePass = climber.getTimePass();

        if(timePass != null){
            timePass.setValidTill(timePass.getValidTill().plusDays(days));
            timePassRepository.save(timePass);
        }
    }

}
