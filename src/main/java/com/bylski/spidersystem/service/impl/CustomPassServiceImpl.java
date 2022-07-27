package com.bylski.spidersystem.service.impl;

import com.bylski.spidersystem.exception.ResourceNotFoundException;
import com.bylski.spidersystem.model.CustomPass;
import com.bylski.spidersystem.model.dto.CustomPassDTO;
import com.bylski.spidersystem.repository.ClimberRepository;
import com.bylski.spidersystem.repository.CustomPassRepository;
import com.bylski.spidersystem.service.inf.CustomPassService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomPassServiceImpl implements CustomPassService {

    private final CustomPassRepository customPassRepository;
    private final ClimberRepository climberRepository;
    private final ModelMapper modelMapper;

    public CustomPassServiceImpl(CustomPassRepository customPassRepository, ClimberRepository climberRepository, ModelMapper modelMapper) {
        this.customPassRepository = customPassRepository;
        this.climberRepository = climberRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createCustomPass(CustomPassDTO customPassDTO) {
        if(
                customPassRepository.existsByCardNumber(customPassDTO.getCardNumber()) ||
                        climberRepository.existsByCardNumber(customPassDTO.getCardNumber())
        ) throw new RuntimeException("card number already taken");

        CustomPass customPass = modelMapper.map(customPassDTO,CustomPass.class);
        customPassRepository.save(customPass);
    }

    @Override
    public CustomPass getCustomPassByCardNumber(String cardNumber) {
        return customPassRepository.findPassByCardNumber(cardNumber)
                .orElseThrow(()-> new ResourceNotFoundException("custom pass","card number",cardNumber));
    }

    @Override
    public Page<CustomPass> getCustomPassByFirstName(String name, Pageable pageable) {
        return customPassRepository.getPassesByFirstName(name,pageable);
    }

    @Override
    public Page<CustomPass> getCustomPassByLastName(String name, Pageable pageable) {
        return customPassRepository.getPassesByLastName(name,pageable);
    }

    @Override
    public void updateCustomPass(Long passId, CustomPassDTO customPassDTO) {
        if (customPassRepository.existsByCardNumber(customPassDTO.getCardNumber()) ||
                climberRepository.existsByCardNumber(customPassDTO.getCardNumber()))
            throw new RuntimeException("card number already taken");

        customPassRepository.findById(passId).map(
                pass -> {
                    pass.setCardNumber(customPassDTO.getCardNumber());
                    pass.setFirstName(customPassDTO.getFirstName());
                    pass.setLastName(customPassDTO.getLastName());
                    pass.setDiscount(customPassDTO.isDiscount());
                    pass.setNote(customPassDTO.getNote());
                    return customPassRepository.save(pass);
                }
        );
    }

    @Override
    public void deleteCustomPass(Long passId) {
        CustomPass pass = customPassRepository.findById(passId)
                .orElseThrow(()-> new ResourceNotFoundException("custom pass", "id", passId));
        customPassRepository.delete(pass);
    }

    @Override
    public void takePunch(Long passId) {

        customPassRepository.findById(passId).map(
                pass -> {
                    pass.setPunches(pass.getPunches()-1);
                    return customPassRepository.save(pass);
                }
        );
    }

    @Override
    public void givePunch(Long passId) {

        customPassRepository.findById(passId).map(
                pass -> {
                    pass.setPunches(pass.getPunches()+1);
                    return customPassRepository.save(pass);
                }
        );
    }
}
