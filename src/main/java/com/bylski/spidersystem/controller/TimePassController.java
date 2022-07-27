package com.bylski.spidersystem.controller;

import com.bylski.spidersystem.model.TimePass;
import com.bylski.spidersystem.model.dto.TimePassDTO;
import com.bylski.spidersystem.service.inf.TimePassService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/timePass/{climberId}")
public class TimePassController {
    private final TimePassService timePassService;
    public TimePassController(TimePassService timePassService) {
        this.timePassService = timePassService;
    }

    @PostMapping("/new")
    public void addTimePass(@PathVariable Long climberId, @RequestBody TimePassDTO timePassDTO){
        timePassService.addTimePass(climberId, timePassDTO);
    }

    @DeleteMapping("/delete")
    public void deleteTimePass(@PathVariable Long climberId){
        timePassService.deleteTimePass(climberId);
    }

    @GetMapping("/get")
    public TimePass getTimePass(@PathVariable Long climberId){
        return timePassService.getPass(climberId);
    }

    @PatchMapping("/renew")
    public void renewTimePass(@PathVariable Long climberId){
        timePassService.renewPass(climberId);
    }

    @PatchMapping("/addDays")
    public void addDays(@PathVariable Long climberId, @RequestParam Integer days){
        timePassService.addDays(climberId, days);
    }

    @PutMapping("/update")
    public void updateTimePass(@PathVariable Long climberId, @RequestBody TimePassDTO timePassDTO){
        timePassService.updatePass(climberId, timePassDTO);
    }


}
