package com.bylski.spidersystem.service.inf;

import com.bylski.spidersystem.model.ClimberAndCustomPass;

public interface ClimberAndCustomPassService {
    ClimberAndCustomPass getEntityByCardNumber(String cardNumber);

}
