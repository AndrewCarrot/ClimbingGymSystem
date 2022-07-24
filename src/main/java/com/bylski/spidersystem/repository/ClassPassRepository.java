package com.bylski.spidersystem.repository;

import com.bylski.spidersystem.model.ClassPass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassPassRepository extends JpaRepository<ClassPass,Long> {
}
