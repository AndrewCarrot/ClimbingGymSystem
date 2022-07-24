package com.bylski.spidersystem.service.impl;

import com.bylski.spidersystem.model.Climber;
import com.bylski.spidersystem.model.ClimberAndCustomPass;
import com.bylski.spidersystem.repository.ClimberRepository;
import com.bylski.spidersystem.repository.CustomPassRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ClimberAndCustomPassServiceImplTest {

    @Mock
    private ClimberRepository climberRepository;

    @Mock
    private CustomPassRepository customPassRepository;

    @InjectMocks
    private ClimberAndCustomPassServiceImpl service;

    @Test
    public void getEntityByCardNumber_nonExistingCardNumber(){
        given(climberRepository.findClimberByCardNumber(anyString())).willReturn(Optional.empty());
        given(customPassRepository.findPassByCardNumber(anyString())).willReturn(Optional.empty());

        ClimberAndCustomPass climberAndCustomPass = service.getEntityByCardNumber("123");

        assertAll(()->{
           assertThat(climberAndCustomPass.getClimber(), equalTo(null));
           assertThat(climberAndCustomPass.getCustomPass(), equalTo(null));
        });
    }

    @Test
    public void getEntityByCardNumber_existingClimber(){
        given(climberRepository.findClimberByCardNumber(anyString())).willReturn(Optional.of(new Climber()));
        given(customPassRepository.findPassByCardNumber(anyString())).willReturn(Optional.empty());

        ClimberAndCustomPass climberAndCustomPass = service.getEntityByCardNumber("123");

        assertAll(()->{
            assertThat(climberAndCustomPass.getClimber(), equalTo(new Climber()));
            assertThat(climberAndCustomPass.getCustomPass(), equalTo(null));
        });

    }

}