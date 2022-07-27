package com.bylski.spidersystem.service.impl;

import com.bylski.spidersystem.exception.ResourceNotFoundException;
import com.bylski.spidersystem.model.ClassFrequency;
import com.bylski.spidersystem.model.ClassPass;
import com.bylski.spidersystem.model.Climber;
import com.bylski.spidersystem.model.dto.ClassPassDTO;
import com.bylski.spidersystem.repository.ClassPassRepository;
import com.bylski.spidersystem.repository.ClimberRepository;
import com.bylski.spidersystem.service.inf.ClassPassService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Stream;

@Service
public class ClassPassServiceImpl implements ClassPassService {

    private final ClassPassRepository classPassRepository;
    private final ClimberRepository climberRepository;

    public ClassPassServiceImpl(ClassPassRepository classPassRepository, ClimberRepository climberRepository) {
        this.classPassRepository = classPassRepository;
        this.climberRepository = climberRepository;
    }

    @Override
    public void addClassPass(Long climberId, ClassPassDTO classPassDTO) {
        Climber climber = climberRepository.findById(climberId)
                .orElseThrow(() -> new ResourceNotFoundException("climber","id",climberId));
        ClassPass classPass = new ClassPass(
                classPassDTO.isDiscount(),
                classPassDTO.getNote(),
                classPassDTO.getClassFrequency(),
                classPassDTO.getValidFrom(),
                classPassDTO.isMultisport()
        );

        climber.setClassPass(classPass);
        climberRepository.save(climber);
    }

    @Override
    public void deleteClassPass(Long climberId) {
        Climber climber = climberRepository.findById(climberId)
                .orElseThrow(() -> new ResourceNotFoundException("climber","id", climberId));
        ClassPass classPass = climber.getClassPass();

        climber.setClassPass(null);
        climberRepository.save(climber);
        classPassRepository.delete(classPass);
    }

    @Override
    public ClassPass getPass(Long climberId) {
        return climberRepository.findById(climberId).map(Climber::getClassPass)
                .orElseThrow(() -> new ResourceNotFoundException("climber","id", climberId));
    }

    @Override
    public void renewPass(Long climberId) {
        Climber climber = climberRepository.findById(climberId)
                .orElseThrow(() -> new ResourceNotFoundException("climber","id", climberId));
        ClassPass classPass = climber.getClassPass();

        if(classPass != null) {
            classPass.setPunches(classPass.getClassFrequency() == ClassFrequency.ONCE_PER_WEEK ? 4 : 8);
            classPass.setValidTill(LocalDate.now().plusDays(30));
            classPassRepository.save(classPass);
        }
    }

    @Override
    public void updatePass(Long climberId, ClassPassDTO classPassDTO) {
        Climber climber = climberRepository.findById(climberId)
                .orElseThrow(() -> new ResourceNotFoundException("climber","id", climberId));
        ClassPass classPass = climber.getClassPass();

        if(classPass != null){
            classPass.setDiscount(classPassDTO.isDiscount());
            classPass.setNote(classPassDTO.getNote());
            classPass.setMultisport(classPassDTO.isMultisport());
            classPass.setClassFrequency(classPassDTO.getClassFrequency());
            classPass.setValidFrom(classPassDTO.getValidFrom());
            classPass.setValidTill(classPassDTO.getValidFrom().plusDays(30));

            classPassRepository.save(classPass);
        }


    }

    @Override
    public void addDays(Long climberId, Integer days) {
        Climber climber = climberRepository.findById(climberId)
                .orElseThrow(() -> new ResourceNotFoundException("climber","id",climberId));
        ClassPass classPass = climber.getClassPass();

        if(classPass != null) {
            classPass.setValidTill(classPass.getValidTill().plusDays(days));
            classPassRepository.save(classPass);
        }

    }

    @Override
    public void givePunches(Long climberId) {
        Climber climber = climberRepository.findById(climberId)
                .orElseThrow(() -> new ResourceNotFoundException("climber","id",climberId));
        ClassPass classPass = climber.getClassPass();

        if(classPass != null){
            classPass.setPunches(classPass.getPunches()+1);
            classPassRepository.save(classPass);
        }
    }

    @Override
    public void takePunches(Long climberId) {
        Climber climber = climberRepository.findById(climberId)
                .orElseThrow(() -> new ResourceNotFoundException("climber","id",climberId));
        ClassPass classPass = climber.getClassPass();

        if(classPass != null){
            classPass.setPunches(classPass.getPunches()-1);
            classPassRepository.save(classPass);
        }
    }
}
