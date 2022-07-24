package com.bylski.spidersystem.service.impl;

import com.bylski.spidersystem.exception.ResourceNotFoundException;
import com.bylski.spidersystem.model.Climber;
import com.bylski.spidersystem.model.PassType;
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
                    punchPass.setType(PassType.PUNCH);
                    climber.setPunchPass(punchPass);
                    return climberRepository.save(climber); // punchPass entity should be saved alongside climber due to cascade type PERSIST
                }
        );

    }

    @Override
    public void deletePunchPass(Long climberId) {

        Climber climber = climberRepository.findById(climberId)
                .orElseThrow(()->new ResourceNotFoundException("climber","id",climberId));
        if (climber.getPunchPass() == null){
            throw new RuntimeException("punch pass is not present");
        }

        punchPassRepository.delete(climber.getPunchPass());
        //climber.setPunchPass(null); // TODO necessary ?
        climberRepository.save(climber);

    }

    @Override
    public PunchPass getPass(Long climberId) { // TODO legal ?

        return climberRepository.findById(climberId).map(
                Climber::getPunchPass
        ).orElseThrow(()->new ResourceNotFoundException("climber","id",climberId));

    }

    @Override
    public void takePunch(Long climberId) {
        climberRepository.findById(climberId).map(
                climber -> {
                    climber.getPunchPass().setPunches(climber.getPunchPass().getPunches()-1);
                    return climberRepository.save(climber); // punchPuss should be persisted due to cascade type PERSIST
                }
        );
    }

    @Override
    public void givePunch(Long climberId) {
        climberRepository.findById(climberId).map(
                climber -> {
                    climber.getPunchPass().setPunches(climber.getPunchPass().getPunches()+1);
                    return climberRepository.save(climber);
                }
        );
    }
}
