package com.bylski.spidersystem.service.impl;

import com.bylski.spidersystem.model.Climber;
import com.bylski.spidersystem.model.CustomPass;
import com.bylski.spidersystem.model.dto.CustomPassDTO;
import com.bylski.spidersystem.repository.ClimberRepository;
import com.bylski.spidersystem.repository.CustomPassRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomPassServiceImplTest {

    @Mock
    private CustomPassRepository customPassRepository;
    @Mock
    private ClimberRepository climberRepository;

    private CustomPassServiceImpl service;
    private CustomPassDTO customPassDTO;
    @Captor
    private ArgumentCaptor<CustomPass> customPassArgumentCaptor;

    @BeforeEach
    void setUp() {
        service = new CustomPassServiceImpl(customPassRepository, climberRepository, new ModelMapper());
        customPassDTO = new CustomPassDTO(
                "123",
                8,
                "Mariusz",
                "Robak",
                true,
                "some note"
        );
    }

    @Test
    void createCustomPass_uniqueCardNumber_shouldPersist() {
        service.createCustomPass(customPassDTO);

        verify(customPassRepository, times(1)).save(customPassArgumentCaptor.capture());

        assertThat(customPassArgumentCaptor.getValue().getCardNumber(), equalTo(customPassDTO.getCardNumber()));
    }

    @Test
    void createCustomPass_cardTakenByClimber_shouldThrowException(){
        given(climberRepository.existsByCardNumber(customPassDTO.getCardNumber())).willReturn(true);

        Throwable cardTakenException = assertThrows(RuntimeException.class, ()-> service.createCustomPass(customPassDTO));

        assertThat(cardTakenException.getMessage(), equalTo("card number already taken"));
    }

    @Test
    void getCustomPassByCardNumber() {
        given(customPassRepository.findPassByCardNumber("123")).willReturn(Optional.of(new CustomPass()));

        service.getCustomPassByCardNumber("123");

        verify(customPassRepository, times(1)).findPassByCardNumber(eq("123"));
    }

    @Test
    void getCustomPassByFirstName() {
        service.getCustomPassByFirstName("Robo", Pageable.unpaged());

        verify(customPassRepository, times(1)).getPassesByFirstName(eq("Robo"), eq(Pageable.unpaged()));
    }

    @Test
    void getCustomPassByLastName() {
        service.getCustomPassByLastName("Robo", Pageable.unpaged());

        verify(customPassRepository, times(1)).getPassesByLastName(eq("Robo"), eq(Pageable.unpaged()));
    }

    @Test
    void updateCustomPass_newCardNumberAvailable_shouldPersist() {
        given(customPassRepository.save(any(CustomPass.class))).willReturn(new CustomPass());
        when(customPassRepository.findById(anyLong())).thenReturn(Optional.of(new CustomPass()));

        service.updateCustomPass(1L,customPassDTO);

        verify(customPassRepository, times(1)).save(customPassArgumentCaptor.capture());

        assertThat(customPassArgumentCaptor.getValue().getFirstName(), equalTo(customPassDTO.getFirstName()));
    }

    @Test
    void updateCustomPass_newCardNumberTaken_shouldThrowException() {
        when(customPassRepository.existsByCardNumber(customPassDTO.getCardNumber())).thenReturn(true);

        Throwable cardNumberTaken = assertThrows(RuntimeException.class, ()-> service.updateCustomPass(1L,customPassDTO));

        assertThat(cardNumberTaken.getMessage(), equalTo("card number already taken"));
    }


    @Test
    void deleteCustomPass() {
        when(customPassRepository.findById(1L)).thenReturn(Optional.of(new CustomPass()));

        service.deleteCustomPass(1L);

        verify(customPassRepository, times(1)).delete(any(CustomPass.class));
    }

    @Test
    void takePunch() {
        CustomPass customPass = new CustomPass();
        customPass.setPunches(8);

        when(customPassRepository.findById(1L)).thenReturn(Optional.of(customPass));

        service.takePunch(1L);

        verify(customPassRepository, times(1)).save(customPassArgumentCaptor.capture());

        assertThat(customPassArgumentCaptor.getValue().getPunches(), equalTo(7));
    }

    @Test
    void givePunch() {
        CustomPass customPass = new CustomPass();
        customPass.setPunches(8);

        when(customPassRepository.findById(1L)).thenReturn(Optional.of(customPass));

        service.givePunch(1L);

        verify(customPassRepository, times(1)).save(customPassArgumentCaptor.capture());

        assertThat(customPassArgumentCaptor.getValue().getPunches(), equalTo(9));
    }
}