package com.bylski.spidersystem.controller;

import com.bylski.spidersystem.model.PunchPass;
import com.bylski.spidersystem.model.dto.PunchPassDTO;
import com.bylski.spidersystem.service.inf.PunchPassService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/punchPass/{climberId}")
public class PunchPassController {
    private final PunchPassService punchPassService;

    public PunchPassController(PunchPassService punchPassService) {
        this.punchPassService = punchPassService;
    }

    @PostMapping("/new")
    public void addPunchPass(@PathVariable Long climberId, @RequestBody PunchPassDTO punchPassDTO){
        punchPassService.addPunchPass(climberId, punchPassDTO);
    }

    @DeleteMapping("/delete")
    public void deletePunchPass(@PathVariable Long climberId){
        punchPassService.deletePunchPass(climberId);
    }

    @GetMapping("/get")
    public PunchPass getPunchPass(@PathVariable Long climberId){
        return punchPassService.getPass(climberId);
    }

    @PatchMapping("/takePunch")
    public void takePunch(@PathVariable Long climberId){
        punchPassService.takePunch(climberId);
    }

    @PatchMapping("/givePunch")
    public void givePunch(@PathVariable Long climberId){
        punchPassService.givePunch(climberId);
    }

}
