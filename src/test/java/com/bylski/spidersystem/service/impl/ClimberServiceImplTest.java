package com.bylski.spidersystem.service.impl;

import com.bylski.spidersystem.exception.ResourceNotFoundException;
import com.bylski.spidersystem.model.Climber;
import com.bylski.spidersystem.model.CustomPass;
import com.bylski.spidersystem.model.dto.ClimberDTO;
import com.bylski.spidersystem.repository.ClimberRepository;
import com.bylski.spidersystem.repository.CustomPassRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClimberServiceImplTest {

   @Mock
   private ClimberRepository climberRepository;
   @Mock
   private CustomPassRepository customPassRepository;

   @Captor
   private ArgumentCaptor<Climber> climberArgumentCaptor;

   @InjectMocks
   private ClimberServiceImpl climberService;

   private Climber climber;
   private ClimberDTO climberDTO;


    @BeforeEach
    void setUp() {
        climberService = new ClimberServiceImpl(climberRepository, customPassRepository, new ModelMapper());
        climber = new Climber(1L,"1","ds","dsd","s","d",null,null,null,null);
        climberDTO = new ClimberDTO("123456789","Andrzej","Marchewka","483736594");
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    public void shouldPersistNewClimber(){
        //given

        //when
        climberService.createClimber(climberDTO);

        //then
        verify(climberRepository, times(1)).save(climberArgumentCaptor.capture());

        Climber climber = climberArgumentCaptor.getValue();

        assertAll(()->{
            assertEquals(climberDTO.getCardNumber(), climber.getCardNumber());
            assertEquals(climberDTO.getFirstName(), climber.getFirstName());
            assertEquals(climberDTO.getLastName(), climber.getLastName());
            assertEquals(climberDTO.getPhoneNumber(), climber.getPhoneNumber());
        });
    }

    @Test
    public void newClimber_shouldThrowExceptionWhenCardNumberIsTaken(){

        given(customPassRepository.findPassByCardNumber(climberDTO.getCardNumber())).willReturn(Optional.of(new CustomPass()));

        Throwable cardTakenException = assertThrows(RuntimeException.class, () ->
            climberService.createClimber(climberDTO)
        );

        assertThat(cardTakenException.getMessage(), equalTo("card number already taken"));

        verify(climberRepository, never()).save(any(Climber.class));

    }

    @Test
    public void shouldCreateNote(){
        //given
        String note = "new note";
        given(climberRepository.findById(anyLong())).willReturn(Optional.of(climber));
        given(climberRepository.save(any())).willReturn(new Climber());

        //when
        climberService.addNote(1L,note);

        //then
        verify(climberRepository, times(1)).save(climberArgumentCaptor.capture());
        assertEquals(note, climberArgumentCaptor.getValue().getNote());
    }

    @Test
    public void changeCardNumber_cardNotTaken_willUpdateClimber(){

        String newCardNumber = "69";
        given(climberRepository.findById(anyLong())).willReturn(Optional.of(climber));
        given(climberRepository.save(any())).willReturn(new Climber());

        climberService.changeCardNumber(1L,newCardNumber);

        verify(climberRepository, times(1)).save(climberArgumentCaptor.capture());
        assertEquals(newCardNumber, climberArgumentCaptor.getValue().getCardNumber());

    }

    @Test
    public void changeCardNumber_cardTaken_willThrowException(){
        given(climberRepository.findClimberByCardNumber(anyString())).willReturn(Optional.ofNullable(climber));

        Throwable cardTakenException = assertThrows(RuntimeException.class, ()-> climberService.changeCardNumber(1L,"1") );

        assertThat(cardTakenException.getMessage(), equalTo("card number already taken"));
    }

    @Test
    public void getClimbersByFirstName_shouldInvokeRightMethodFromRepository(){
        climberService.getClimbersByFirstName("Martinez", Pageable.unpaged());

        verify(climberRepository, times(1)).getClimbersByFirstNameContainingIgnoreCase(eq("Martinez"),any());
    }

    @Test
    public void getClimbersByLastName_shouldInvokeRightMethodFromRepository(){
        climberService.getClimbersByLastName("Fernando", Pageable.unpaged());

        verify(climberRepository, times(1)).getClimbersByLastNameContainingIgnoreCase(eq("Fernando"), any());
    }


    @Test
    public void getClimberByCardNumber_shouldInvokeRightMethodFromRepository(){
        given(climberRepository.findClimberByCardNumber("123")).willReturn(Optional.ofNullable(climber));

        climberService.getClimberByCardNumber("123");

        verify(climberRepository, times(1)).findClimberByCardNumber(eq("123"));
    }

    @Test
    public void getClimberByCardNumber_noMatch_shouldThrowResourceNotFoundException(){
        given(climberRepository.findClimberByCardNumber(anyString())).willReturn(Optional.empty());

        Throwable climberNotFoundException = assertThrows(ResourceNotFoundException.class, ()-> climberService.getClimberByCardNumber("123") );

        assertThat(climberNotFoundException.getMessage(), equalTo("climber not found with card number : '123'"));
    }


    @Test
    public void updateClimber_cardNumberAvailable_shouldPersistUpdatedEntity(){
        given(climberRepository.findById(anyLong())).willReturn(Optional.ofNullable(climber));
        given(climberRepository.save(any())).willReturn(new Climber());

        climberService.updateClimber(1L,climberDTO);

        verify(climberRepository, times(1)).save(climberArgumentCaptor.capture());

        Climber climber1 = climberArgumentCaptor.getValue();

        assertAll(()->{
            assertThat(climber1.getCardNumber(), equalTo(climberDTO.getCardNumber()));
            assertThat(climber1.getPhoneNumber(), equalTo(climberDTO.getPhoneNumber()));
        });
    }

    @Test
    public void updateClimber_cardNumberTaken_shouldThrowException(){
        given(climberRepository.findClimberByCardNumber(climberDTO.getCardNumber())).willReturn(Optional.of(climber));

        Throwable cardTakenException = assertThrows(RuntimeException.class,
                ()-> climberService.updateClimber(1L,climberDTO) );

        assertThat(cardTakenException.getMessage(), equalTo("card number already taken"));


    }

    @Test
    public void deleteClimber_existingClimber_shouldInvokeRepositoryDeleteMethod(){
        given(climberRepository.findById(1L)).willReturn(Optional.ofNullable(climber));

        climberService.deleteClimber(1L);

        verify(climberRepository, times(1)).delete(climber);
    }

    @Test
    public void deleteNote_climberExists_shouldSetNoteToNull(){
        given(climberRepository.findById(1L)).willReturn(Optional.of(climber));
        given(climberRepository.save(any())).willReturn(climber);

        climberService.deleteNote(1L);

        verify(climberRepository, times(1)).save(climberArgumentCaptor.capture());

        assertThat(climberArgumentCaptor.getValue().getNote(), equalTo(null));
    }



}