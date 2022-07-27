package com.bylski.spidersystem.service.impl;

import com.bylski.spidersystem.exception.ResourceNotFoundException;
import com.bylski.spidersystem.model.Climber;
import com.bylski.spidersystem.model.PunchPass;
import com.bylski.spidersystem.model.dto.PunchPassDTO;
import com.bylski.spidersystem.repository.ClimberRepository;
import com.bylski.spidersystem.repository.PunchPassRepository;
import com.bylski.spidersystem.service.inf.PunchPassService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PunchPassServiceImpl implements PunchPassService {

    private final PunchPassRepository punchPassRepository;
    private final ClimberRepository climberRepository;
    private final ModelMapper modelMapper;

    public PunchPassServiceImpl(PunchPassRepository punchPassRepository, ClimberRepository climberRepository, ModelMapper modelMapper) {
        this.punchPassRepository = punchPassRepository;
        this.climberRepository = climberRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addPunchPass(Long climberId, PunchPassDTO punchPassDTO) {

        climberRepository.findById(climberId).map(
                climber -> {
                    if (climber.getPunchPass() != null)
                        throw new RuntimeException("punch pass is already present");
                    PunchPass punchPass = modelMapper.map(punchPassDTO,PunchPass.class);
                    climber.setPunchPass(punchPass);
                    return climberRepository.save(climber);
                }
        );

    }

    @Override
    public void deletePunchPass(Long climberId) {
        Climber climber = climberRepository.findById(climberId)
                .orElseThrow(()->new ResourceNotFoundException("climber","id",climberId));
        PunchPass punchPass = climber.getPunchPass();

        climber.setPunchPass(null);
        climberRepository.save(climber);
        punchPassRepository.delete(punchPass);
    }

    @Override
    public PunchPass getPass(Long climberId) {
        return climberRepository.findById(climberId).map(Climber::getPunchPass)
                .orElseThrow(()->new ResourceNotFoundException("climber","id",climberId));

    }

    @Override
    public void takePunch(Long climberId) {
        Climber climber = climberRepository.findById(climberId)
                .orElseThrow(() -> new ResourceNotFoundException("climber","id", climberId));
        PunchPass punchPass = climber.getPunchPass();

        if(punchPass != null) {
            punchPass.setPunches(punchPass.getPunches() - 1);
            punchPassRepository.save(punchPass);
        }
    }

    @Override
    public void givePunch(Long climberId) {
        Climber climber = climberRepository.findById(climberId)
                .orElseThrow(() -> new ResourceNotFoundException("climber","id", climberId));
        PunchPass punchPass = climber.getPunchPass();

        if(punchPass != null) {
            punchPass.setPunches(punchPass.getPunches() + 1);
            punchPassRepository.save(punchPass);
        }
    }
}
