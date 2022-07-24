package com.bylski.spidersystem.repository;

import com.bylski.spidersystem.model.PunchPass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PunchPassRepository extends JpaRepository<PunchPass,Long> {
}
