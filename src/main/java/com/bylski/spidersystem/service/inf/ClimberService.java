package com.bylski.spidersystem.service.inf;

import com.bylski.spidersystem.model.Climber;
import com.bylski.spidersystem.model.dto.ClimberDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClimberService {
    void createClimber(ClimberDTO climberDTO);
    void addNote(Long climberId, String note);

    Page<Climber> getAllClimbers(Pageable pageable);
    Page<Climber> getClimbersByFirstName(String firstName, Pageable pageable);
    Page<Climber> getClimbersByLastName(String lastName, Pageable pageable);
    Climber getClimberByCardNumber(String cardNumber);

    void updateClimber(Long climberId, ClimberDTO climberDTO);
    void changeCardNumber(Long climberId, String newCardNumber);

    void deleteClimber(Long climberId);
    void deleteNote(Long climberId);
}
