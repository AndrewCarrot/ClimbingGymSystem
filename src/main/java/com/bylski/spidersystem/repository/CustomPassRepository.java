package com.bylski.spidersystem.repository;

import com.bylski.spidersystem.model.CustomPass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomPassRepository extends JpaRepository<CustomPass, Long> {
    boolean existsByCardNumber(String cardNumber);
    Optional<CustomPass> findPassByCardNumber(String cardNumber);
    Page<CustomPass> getPassesByFirstName(String firstName, Pageable pageable);
    Page<CustomPass> getPassesByLastName(String lastName, Pageable pageable);

}
