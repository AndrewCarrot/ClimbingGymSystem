package com.bylski.spidersystem.service.inf;

import com.bylski.spidersystem.model.PunchPass;
import com.bylski.spidersystem.model.dto.PunchPassDTO;

public interface PunchPassService {
    void addPunchPass(Long climberId, PunchPassDTO punchPassDTO);
    void deletePunchPass(Long climberId);

    PunchPass getPass(Long climberId);

    void takePunch(Long climberId);
    void givePunch(Long climberId);

}
