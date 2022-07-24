package com.bylski.spidersystem.repository;

import com.bylski.spidersystem.model.TimePass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimePassRepository extends JpaRepository<TimePass, Long> {
}
