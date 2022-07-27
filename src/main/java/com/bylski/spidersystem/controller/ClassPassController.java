package com.bylski.spidersystem.controller;

import com.bylski.spidersystem.model.ClassPass;
import com.bylski.spidersystem.model.dto.ClassPassDTO;
import com.bylski.spidersystem.service.inf.ClassPassService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/classPass/{climberId}")
public class ClassPassController {

    private final ClassPassService classPassService;

    public ClassPassController(ClassPassService classPassService) {
        this.classPassService = classPassService;
    }

    @PostMapping("/new")
    public void addClassPass(@PathVariable Long climberId, @RequestBody ClassPassDTO classPassDTO){
        classPassService.addClassPass(climberId, classPassDTO);
    }

    @DeleteMapping("/delete")
    public void deleteClassPass(@PathVariable Long climberId){
        classPassService.deleteClassPass(climberId);
    }

    @GetMapping("/get")
    public ClassPass getClassPass(@PathVariable Long climberId){
        return classPassService.getPass(climberId);
    }

    @PatchMapping("/renew")
    public void renewClassPass(@PathVariable Long climberId){
        classPassService.renewPass(climberId);
    }

    @PatchMapping("/addDays")
    public void addDays(@PathVariable Long climberId,@RequestParam Integer days){
        classPassService.addDays(climberId, days);
    }

    @PatchMapping("/givePunch")
    public void givePunches(@PathVariable Long climberId){
        classPassService.givePunches(climberId);
    }

    @PatchMapping("/takePunch")
    public void takePunches(@PathVariable Long climberId){
        classPassService.takePunches(climberId);
    }

    @PutMapping("/update")
    public void updateClassPass(@PathVariable Long climberId, @RequestBody ClassPassDTO classPassDTO){
        classPassService.updatePass(climberId, classPassDTO);
    }



}
