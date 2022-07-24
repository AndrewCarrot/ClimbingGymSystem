package com.bylski.spidersystem.service.inf;

import com.bylski.spidersystem.model.TimePass;
import com.bylski.spidersystem.model.dto.TimePassDTO;

public interface TimePassService {
    void addTimePass(Long climberId, TimePassDTO timePassDTO);
    void deleteTimePass(Long climberId);

    TimePass getPass(Long climberId);

    void renewPass(Long climberId);

    void updatePass(Long climberId, TimePassDTO timePassDTO);

    void addDays(Long climberId, Integer days);

}
