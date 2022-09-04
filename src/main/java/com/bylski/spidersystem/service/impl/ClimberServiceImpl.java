package com.bylski.spidersystem.service.impl;

import com.bylski.spidersystem.exception.ResourceNotFoundException;
import com.bylski.spidersystem.model.Climber;
import com.bylski.spidersystem.model.CustomPass;
import com.bylski.spidersystem.model.dto.ClimberDTO;
import com.bylski.spidersystem.repository.ClimberRepository;
import com.bylski.spidersystem.repository.CustomPassRepository;
import com.bylski.spidersystem.service.inf.ClimberService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClimberServiceImpl implements ClimberService {

    private final ClimberRepository climberRepository;
    private final CustomPassRepository customPassRepository;
    private final ModelMapper modelMapper;

    public ClimberServiceImpl(ClimberRepository climberRepository, CustomPassRepository customPassRepository, ModelMapper modelMapper){
        this.climberRepository = climberRepository;
        this.customPassRepository = customPassRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createClimber(ClimberDTO climberDTO) {
        Optional<Climber> x = climberRepository.findClimberByCardNumber(climberDTO.getCardNumber());
        Optional<CustomPass> y = customPassRepository.findPassByCardNumber(climberDTO.getCardNumber());
        if(x.isPresent() || y.isPresent())
            throw new RuntimeException("card number already taken");
        Climber climber = modelMapper.map(climberDTO,Climber.class);
        climberRepository.save(climber);
    }

    @Override
    public void addNote(Long climberId, String note) {
        climberRepository.findById(climberId).map(
                climber->{
                    climber.setNote(note);
                    return climberRepository.save(climber);
                }).orElseThrow(()->new ResourceNotFoundException("climber","id",climberId));
    }

    @Override
    public void changeCardNumber(Long climberId, String newCardNumber) {
        Optional<Climber> climber = climberRepository.findClimberByCardNumber(newCardNumber);
        if(climber.isPresent())
            throw new RuntimeException("card number already taken");

        climberRepository.findById(climberId).map(
                x -> {
                    x.setCardNumber(newCardNumber);
                    return climberRepository.save(x);
                }).orElseThrow(()-> new ResourceNotFoundException("climber","id",climberId));
    }

    @Override
    public Page<Climber> getAllClimbers(Pageable pageable){
        return climberRepository.findAll(pageable);
    }
    @Override
    public Page<Climber> getClimbersByFirstName(String firstName, Pageable pageable) {
        return climberRepository.getClimbersByFirstNameContainingIgnoreCase(firstName, pageable);
    }

    @Override
    public Page<Climber> getClimbersByLastName(String lastName, Pageable pageable) {
        return climberRepository.getClimbersByLastNameContainingIgnoreCase(lastName, pageable);
    }

    @Override
    public Climber getClimberByCardNumber(String cardNumber) {
        return climberRepository.findClimberByCardNumber(cardNumber)
                .orElseThrow(()-> new ResourceNotFoundException("climber","card number",cardNumber));
    }

    @Override
    public void updateClimber(Long climberId, ClimberDTO climberData) {
        Optional<Climber> x = climberRepository.findClimberByCardNumber(climberData.getCardNumber());
        Climber climber = climberRepository.findById(climberId)
                .orElseThrow(() -> new ResourceNotFoundException("climber","id",climberId));

        if (x.isPresent() && !x.get().getCardNumber().equals(climber.getCardNumber()))
            throw new RuntimeException("card number already taken");


        climber.setCardNumber(climberData.getCardNumber());
        climber.setFirstName(climberData.getFirstName());
        climber.setLastName(climberData.getLastName());
        climber.setPhoneNumber(climberData.getPhoneNumber());

        climberRepository.save(climber);

    }


    @Override
    public void deleteClimber(Long climberId) {
        Climber climber = climberRepository.findById(climberId)
                .orElseThrow(()-> new ResourceNotFoundException("climber","id",climberId));
        climberRepository.delete(climber);
    }

    @Override
    public void deleteNote(Long climberId) {
        climberRepository.findById(climberId).map(
                climber -> {
                    climber.setNote(null);
                    return climberRepository.save(climber);
                }) .orElseThrow(()-> new ResourceNotFoundException("climber","id",climberId));
    }

}
