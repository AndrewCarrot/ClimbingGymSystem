package com.bylski.spidersystem.controller;

import com.bylski.spidersystem.model.Climber;
import com.bylski.spidersystem.model.dto.ClimberDTO;
import com.bylski.spidersystem.service.inf.ClimberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/climbers")
public class ClimberController {


    private final ClimberService climberService;

    public ClimberController(ClimberService climberService) {
        this.climberService = climberService;
    }

    @PostMapping
    public void createClimber(@RequestBody @Valid ClimberDTO climberDTO){
        climberService.createClimber(climberDTO);
    }

    @GetMapping("/all")
    public Page<Climber> getAllClimbers(Pageable pageable){
        return climberService.getAllClimbers(pageable);
    }

    @GetMapping("/by-first-name")
    public Page<Climber> getClimbersByFirstName(@RequestParam String firstName, Pageable pageable){
        return climberService.getClimbersByFirstName(firstName,pageable);
    }

    @GetMapping("/by-last-name")
    public Page<Climber> getClimbersByLastName(@RequestParam String lastName, Pageable pageable){
        return climberService.getClimbersByLastName(lastName, pageable);
    }

    @GetMapping("/by-card-number")
    public Climber getClimberByCardNumber(@RequestParam String cardNumber){
        return climberService.getClimberByCardNumber(cardNumber);
    }

    @PutMapping("/update")
    public void updateClimber(@RequestParam Long climberId, @RequestBody ClimberDTO climberDTO){
        climberService.updateClimber(climberId, climberDTO);
    }

    @PatchMapping("/update/cardNumber")
    public void changeCardNumber(@RequestParam Long climberId, @RequestBody String cardNumber){
        climberService.changeCardNumber(climberId, cardNumber);
    }

    @PatchMapping("/update/note")
    public void addNote(@RequestParam Long climberId, @RequestBody String note){
        climberService.addNote(climberId, note);
    }

    @PatchMapping("/delete/note")
    public void deleteNote(@RequestParam Long climberId){
        climberService.deleteNote(climberId);
    }

    @DeleteMapping("/delete")
    public void deleteClimber(@RequestParam Long climberId){
        climberService.deleteClimber(climberId);
    }
}
