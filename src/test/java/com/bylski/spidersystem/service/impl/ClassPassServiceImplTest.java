package com.bylski.spidersystem.service.impl;

import com.bylski.spidersystem.model.ClassFrequency;
import com.bylski.spidersystem.model.ClassPass;
import com.bylski.spidersystem.model.Climber;
import com.bylski.spidersystem.model.dto.ClassPassDTO;
import com.bylski.spidersystem.repository.ClassPassRepository;
import com.bylski.spidersystem.repository.ClimberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClassPassServiceImplTest {

    @Mock
    private ClassPassRepository classPassRepository;
    @Mock
    private ClimberRepository climberRepository;

    @InjectMocks
    private ClassPassServiceImpl service;
    @Captor
    private ArgumentCaptor<ClassPass> classPassArgumentCaptor;
    @Captor
    private ArgumentCaptor<Climber> climberArgumentCaptor;

    private ModelMapper modelMapper;
    private ClassPassDTO classPassDTO;

    @BeforeEach
    void setUp() {
        classPassDTO = new ClassPassDTO(
                false,
                "some note",
                ClassFrequency.TWICE_PER_WEEK,
                LocalDate.now(),
                true
        );

        modelMapper = new ModelMapper();
    }

    @Test
    void addClassPass() {
        when(climberRepository.findById(anyLong())).thenReturn(Optional.of(new Climber()));

        service.addClassPass(1L,classPassDTO);

        verify(climberRepository, times(1)).save(climberArgumentCaptor.capture());

        ClassPass actual = climberArgumentCaptor.getValue().getClassPass();

        assertThat(actual.getNote(), equalTo(classPassDTO.getNote()));
        assertThat(actual.getPunches(), equalTo(8));

    }

    @Test
    void deleteClassPass() {

    }

    @Test
    void getPass() {
        Climber climber = new Climber();
        climber.setClassPass(modelMapper.map(classPassDTO, ClassPass.class));

        when(climberRepository.findById(anyLong())).thenReturn(Optional.of(climber));

        ClassPass classPass = service.getPass(1L);

        assertThat(classPass, equalTo(climber.getClassPass()));

    }

    @Test
    void renewPass() {

    }

    @Test
    void updatePass() {
    }

    @Test
    void addDays() {
    }

    @Test
    void givePunches() {
    }

    @Test
    void takePunches() {
    }
}