package com.bylski.spidersystem.repository;

import com.bylski.spidersystem.model.Climber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClimberRepository extends JpaRepository<Climber,Long> {
    Page<Climber> getClimbersByFirstNameContainingIgnoreCase(String firstName, Pageable pageable);
    Page<Climber> getClimbersByLastNameContainingIgnoreCase(String lastName, Pageable pageable);

    Optional<Climber> findClimberByCardNumber(String cardNumber);

    boolean existsByCardNumber(String cardNumber);
}
