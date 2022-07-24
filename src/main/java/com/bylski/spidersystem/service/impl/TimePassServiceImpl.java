package com.bylski.spidersystem.service.impl;

import com.bylski.spidersystem.exception.ResourceNotFoundException;
import com.bylski.spidersystem.model.Climber;
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

        timePassRepository.save(timePass);
        climber.setTimePass(timePass);
        climberRepository.save(climber);

    }

    @Override
    public void deleteTimePass(Long climberId) {
        Climber climber = climberRepository.findById(climberId)
                .orElseThrow(()->new ResourceNotFoundException("climber","id",climberId));

        timePassRepository.delete(climber.getTimePass()); // TODO will climber get null in the TimePass field?
        climberRepository.save(climber);

    }

    @Override
    public TimePass getPass(Long climberId) {
        return climberRepository.findById(climberId).map(Climber::getTimePass)
                .orElseThrow(()->new ResourceNotFoundException("climber","id",climberId));
    }

    @Override
    public void renewPass(Long climberId) {
        climberRepository.findById(climberId).map(
                    climber -> {
                        climber.getTimePass().setValidFrom(LocalDate.now());
                        climber.getTimePass().setValidTill(
                                climber.getTimePass().getValidFrom().plusDays(climber.getTimePass().getDuration())
                        );
                        return climberRepository.save(climber); // timePass entity should be persisted alongside climber
                    }
                ).orElseThrow(() -> new ResourceNotFoundException("climber","id",climberId));
    }

    @Override
    public void updatePass(Long climberId, TimePassDTO timePassDTO) {
       climberRepository.findById(climberId).map(
               climber -> {
                   climber.getTimePass().setDiscount(timePassDTO.isDiscount());
                   climber.getTimePass().setNote(timePassDTO.getNote());
                   climber.getTimePass().setValidFrom(timePassDTO.getValidFrom());
                   climber.getTimePass().setValidTill(
                           timePassDTO.getValidFrom().plusDays(timePassDTO.getDuration())
                   );
                   return climberRepository.save(climber);
               }
       );

    }

    @Override
    public void addDays(Long climberId, Integer days) {
        climberRepository.findById(climberId).map(
                climber -> {
                    climber.getTimePass().setValidTill(
                            climber.getTimePass().getValidTill().plusDays(days)
                    );
                    return climberRepository.save(climber);
                }
        ).orElseThrow(() -> new ResourceNotFoundException("climber","id",climberId));
    }
}
