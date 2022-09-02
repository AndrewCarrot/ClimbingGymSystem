package com.bylski.spidersystem.service.impl;

import com.bylski.spidersystem.model.Climber;
import com.bylski.spidersystem.model.ClimbingGroup;
import com.bylski.spidersystem.model.ClimbingGroupType;
import com.bylski.spidersystem.model.dto.ClimbingGroupDTO;
import com.bylski.spidersystem.repository.ClimberRepository;
import com.bylski.spidersystem.repository.ClimbingGroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClimbingGroupServiceImplTest {

    @Mock
    private ClimbingGroupRepository climbingGroupRepository;

    @Mock
    private ClimberRepository climberRepository;

    private ClimbingGroupServiceImpl service;

    private ClimbingGroupDTO climbingGroupDTO;
    @Captor
    private ArgumentCaptor<ClimbingGroup> climbingGroupArgumentCaptor;

    @BeforeEach
    void setUp() {
        service = new ClimbingGroupServiceImpl(climbingGroupRepository, climberRepository, new ModelMapper());
        climbingGroupDTO = new ClimbingGroupDTO("Z1","Karol", ClimbingGroupType.ADVANCED, DayOfWeek.MONDAY, LocalTime.of(19,0));
    }


    @Test
    public void createGroup_shouldInvokeMethodWithGivenParameters() {
        service.createGroup(climbingGroupDTO);

        verify(climbingGroupRepository, times(1)).save(climbingGroupArgumentCaptor.capture());
        assertThat(climbingGroupArgumentCaptor.getValue().getCoach(), equalTo(climbingGroupDTO.getCoach()));
    }

    @Test
    public void createGroup_groupWithTheSameNameExists_shouldThrowException(){
        given(climbingGroupRepository.findClimbingGroupByName(anyString())).willReturn(Optional.of(new ClimbingGroup()));

        Throwable nameTakenException  = assertThrows(RuntimeException.class, ()-> service.createGroup(climbingGroupDTO) );

        assertThat(nameTakenException.getMessage(), equalTo("group with name: " + climbingGroupDTO.getName() + " already exists"));
    }

    @Test
    public void createGroup_coachHasAlreadyGroupAtGivenTime_shouldThrowException(){
        ClimbingGroup climbingGroup = new ClimbingGroup();
        climbingGroup.setClassTime(climbingGroupDTO.getClassTime());
        climbingGroup.setDayOfWeek(climbingGroupDTO.getDayOfWeek());

        given(climbingGroupRepository.getClimbingGroupsByCoach(any())).willReturn(List.of(climbingGroup));

        Throwable dateClashException = assertThrows(RuntimeException.class, ()-> service.createGroup(climbingGroupDTO));

        assertThat(dateClashException.getMessage(), equalTo("coach can't have two groups at the same time"));

        verify(climbingGroupRepository, never()).save(any());
    }

    @Test
    public void createGroup_noDateClashes_shouldPersist(){
        ClimbingGroup climbingGroup = new ClimbingGroup();
        climbingGroup.setClassTime(climbingGroupDTO.getClassTime().plusHours(1));
        climbingGroup.setDayOfWeek(climbingGroupDTO.getDayOfWeek());

        given(climbingGroupRepository.getClimbingGroupsByCoach(any())).willReturn(List.of(climbingGroup));

        service.createGroup(climbingGroupDTO);

        verify(climbingGroupRepository, times(1)).save(any());
    }

    @Test
    void getGroupsByDayOfWeek_ShouldInvokeMethodWithGivenParameter() {
        service.getGroupsByDayOfWeek(DayOfWeek.MONDAY);

        verify(climbingGroupRepository, times(1)).getClimbingGroupsByDayOfWeek(eq(DayOfWeek.MONDAY));
    }

    @Test
    void getGroupsByCoach_shouldInvokeMethodWithGivenParameter() {
        service.getGroupsByCoach("Karol");

        verify(climbingGroupRepository, times(1)).getClimbingGroupsByCoach(eq("Karol"));
    }

    @Test
    void getGroupsByType_shouldInvokeMethodWithGivenParameter() {
        service.getGroupsByType(ClimbingGroupType.ADVANCED);

        verify(climbingGroupRepository, times(1)).getClimbingGroupsByType(eq(ClimbingGroupType.ADVANCED));
    }

    @Test
    void getAllGroups_shouldInvokeFindAll() {
        service.getAllGroups();

        verify(climbingGroupRepository, times(1)).findAll();
    }

    @Test
    void updateGroup_shouldPersistUpdatedEntity() {
        given(climbingGroupRepository.findById(anyLong())).willReturn(Optional.of(new ClimbingGroup()));
        when(climbingGroupRepository.save(any())).thenReturn(new ClimbingGroup());

        service.updateGroup(1L,climbingGroupDTO);

        verify(climbingGroupRepository, times(1)).save(climbingGroupArgumentCaptor.capture());
        assertThat(climbingGroupArgumentCaptor.getValue().getCoach(), equalTo(climbingGroupDTO.getCoach()));
    }

    @Disabled
    @Test
    void addClimberToGroup_shouldPersistUpdatedEntities() {
        Climber climber = new Climber();
        ClimbingGroup climbingGroup = new ClimbingGroup();

        assertThat(climber.getClimbingGroups(), is(nullValue()));
        assertThat(climbingGroup.getClimbers(), is(empty()));

        given(climberRepository.findById(anyLong())).willReturn(Optional.of(climber));
        given(climbingGroupRepository.findById(anyLong())).willReturn(Optional.of(climbingGroup));

        service.addClimberToGroup(1L,1L);

        verify(climbingGroupRepository, times(1)).save(any());

        assertThat(climbingGroup.getClimbers(), is(not(empty())));
        assertThat(climber.getClimbingGroups(), is(notNullValue()));

        verify(climbingGroupRepository, times(1)).save(any());
    }

    @Disabled
    @Test
    void deleteClimberFromGroup() {
        Climber climber = new Climber();
        ClimbingGroup climbingGroup = new ClimbingGroup();
        climbingGroup.addClimber(climber);

        assertThat(climber.getClimbingGroups(), is(notNullValue()));
        assertThat(climbingGroup.getClimbers(), is(not(empty())));

        given(climberRepository.findById(anyLong())).willReturn(Optional.of(climber));
        given(climbingGroupRepository.findById(anyLong())).willReturn(Optional.of(climbingGroup));

        service.deleteClimberFromGroup(1L,1L);

        verify(climbingGroupRepository, times(1)).save(any());

        assertThat(climbingGroup.getClimbers(), is(empty()));
        assertThat(climber.getClimbingGroups(), is(nullValue()));

        verify(climbingGroupRepository, times(1)).save(any());

    }

    @Test
    void deleteGroup() {
        given(climbingGroupRepository.findById(anyLong())).willReturn(Optional.of(new ClimbingGroup()));

        service.deleteGroup(1L);

        verify(climbingGroupRepository, times(1)).delete(any());
    }
}