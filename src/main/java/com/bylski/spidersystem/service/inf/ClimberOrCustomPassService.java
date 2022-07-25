package com.bylski.spidersystem.service.inf;

import com.bylski.spidersystem.model.ClimberOrCustomPass;

public interface ClimberOrCustomPassService {
    ClimberOrCustomPass getEntityByCardNumber(String cardNumber);

}
