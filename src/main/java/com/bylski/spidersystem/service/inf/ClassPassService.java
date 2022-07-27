package com.bylski.spidersystem.service.inf;

import com.bylski.spidersystem.model.ClassPass;
import com.bylski.spidersystem.model.dto.ClassPassDTO;

public interface ClassPassService {
    void addClassPass(Long climberId, ClassPassDTO classPassDTO);
    void deleteClassPass(Long climberId);

    ClassPass getPass(Long climberId);

    void renewPass(Long climberId);

    void updatePass(Long climberId, ClassPassDTO classPassDTO);

    void addDays(Long climberId, Integer days);

    void givePunches(Long climberId);

    void takePunches(Long climberId);
}
