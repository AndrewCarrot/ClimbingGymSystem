package com.bylski.spidersystem.controller;

import com.bylski.spidersystem.model.CustomPass;
import com.bylski.spidersystem.model.dto.CustomPassDTO;
import com.bylski.spidersystem.service.inf.CustomPassService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customPass")
public class CustomPassController {

    private final CustomPassService customPassService;

    public CustomPassController(CustomPassService customPassService) {
        this.customPassService = customPassService;
    }

    @PostMapping("/new")
    public void createNewCustomPass(@RequestBody CustomPassDTO customPassDTO){
        customPassService.createCustomPass(customPassDTO);
    }

    @GetMapping("/get/by-card-number")
    public CustomPass getCustomPassByCardNumber(@RequestParam String cardNumber){
        return customPassService.getCustomPassByCardNumber(cardNumber);
    }

    @GetMapping("/get/by-first-name")
    public Page<CustomPass> getCustomPassesByFirstName(@RequestParam String firstName, Pageable pageable){
        return customPassService.getCustomPassByFirstName(firstName, pageable);
    }

    @GetMapping("/get/by-last-name")
    public Page<CustomPass> getCustomPassesByLastName(@RequestParam String lastName, Pageable pageable){
        return customPassService.getCustomPassByLastName(lastName, pageable);
    }

    @PutMapping("/update")
    public void updateCustomPass(@RequestParam Long passId, @RequestBody CustomPassDTO customPassDTO){
        customPassService.updateCustomPass(passId, customPassDTO);
    }

    @PatchMapping("/take-punch")
    public void takePunch(@RequestParam Long passId){
        customPassService.takePunch(passId);
    }

    @PatchMapping("/give-punch")
    public void givePunch(@RequestParam Long passId){
        customPassService.givePunch(passId);
    }

    @DeleteMapping("/delete")
    public void deleteCustomPass(@RequestParam Long passId){
        customPassService.deleteCustomPass(passId);
    }


}
