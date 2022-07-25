package com.bylski.spidersystem.controller;

import com.bylski.spidersystem.model.ClimberOrCustomPass;
import com.bylski.spidersystem.service.inf.ClimberOrCustomPassService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/card-associated")
public class ClimberOrCustomPassController {

    private final ClimberOrCustomPassService climberOrCustomPassService;

    public ClimberOrCustomPassController(ClimberOrCustomPassService climberOrCustomPassService) {
        this.climberOrCustomPassService = climberOrCustomPassService;
    }

    @GetMapping("/get")
    public ClimberOrCustomPass getByCardNumber(@RequestParam String cardNumber){
        return climberOrCustomPassService.getEntityByCardNumber(cardNumber);
    }
}
