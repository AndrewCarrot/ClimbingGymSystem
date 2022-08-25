package com.bylski.spidersystem.service.impl;

import com.bylski.spidersystem.exception.ResourceNotFoundException;
import com.bylski.spidersystem.model.Climber;
import com.bylski.spidersystem.model.ClimbingGroup;
import com.bylski.spidersystem.model.ClimbingGroupType;
import com.bylski.spidersystem.model.dto.ClimbingGroupDTO;
import com.bylski.spidersystem.repository.ClimberRepository;
import com.bylski.spidersystem.repository.ClimbingGroupRepository;
import com.bylski.spidersystem.service.inf.ClimbingGroupService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Service
public class ClimbingGroupServiceImpl implements ClimbingGroupService {

    private final ClimbingGroupRepository climbingGroupRepository;
    private final ClimberRepository climberRepository;
    private final ModelMapper modelMapper;

    public ClimbingGroupServiceImpl(ClimbingGroupRepository climbingGroupRepository, ClimberRepository climberRepository, ModelMapper modelMapper) {
        this.climbingGroupRepository = climbingGroupRepository;
        this.climberRepository = climberRepository;
        this.modelMapper = modelMapper;
    }

    private void groupDataClashCheck(ClimbingGroupDTO climbingGroupDTO) {
        Optional<ClimbingGroup> group = climbingGroupRepository.findClimbingGroupByName(climbingGroupDTO.getName());
        if(group.isPresent())
            throw new RuntimeException("group with name: " + climbingGroupDTO.getName() + " already exists");

        List<ClimbingGroup> groups = climbingGroupRepository.getClimbingGroupsByCoach(climbingGroupDTO.getCoach());
        Optional<ClimbingGroup> anyGroup = groups.stream()
                .filter(x->x.getDayOfWeek().equals(climbingGroupDTO.getDayOfWeek()))
                .filter(x->x.getClassTime().equals(climbingGroupDTO.getClassTime()))
                .findFirst();
        if (anyGroup.isPresent())
            throw new RuntimeException("coach can't have two groups at the same time");
    }

    @Override
    public ClimbingGroup getGroupByName(String name){
        return climbingGroupRepository.findClimbingGroupByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("group","name",name));
    }
    @Override
    public void createGroup(ClimbingGroupDTO climbingGroupDTO) {
        groupDataClashCheck(climbingGroupDTO);

        ClimbingGroup climbingGroup = modelMapper.map(climbingGroupDTO,ClimbingGroup.class);

        // 25.08
        climbingGroup.setDuration(climbingGroup.getType().getDuration());

        climbingGroupRepository.save(climbingGroup);
    }

    @Override
    public List<ClimbingGroup> getGroupsByDayOfWeek(DayOfWeek dayOfWeek) {
        return climbingGroupRepository.getClimbingGroupsByDayOfWeek(dayOfWeek);
    }

    @Override
    public List<ClimbingGroup> getGroupsByCoach(String coach) {
        return climbingGroupRepository.getClimbingGroupsByCoach(coach);
    }

    @Override
    public List<ClimbingGroup> getGroupsByType(ClimbingGroupType type) {
        return climbingGroupRepository.getClimbingGroupsByType(type);
    }

    @Override
    public List<ClimbingGroup> getAllGroups() {
        return climbingGroupRepository.findAll();
    }

    @Override
    public void updateGroup(Long id, ClimbingGroupDTO climbingGroupDTO) {
        groupDataClashCheck(climbingGroupDTO);

        climbingGroupRepository.findById(id).map(
                g -> {
                    g.setName(climbingGroupDTO.getName());
                    g.setCoach(climbingGroupDTO.getCoach());
                    g.setDayOfWeek(climbingGroupDTO.getDayOfWeek());
                    g.setClassTime(climbingGroupDTO.getClassTime());
                    return climbingGroupRepository.save(g);
                }
        );
    }


    @Override
    public void addClimberToGroup(Long climberId, Long climbingGroupId){
        Climber climber = climberRepository.findById(climberId)
                .orElseThrow(()-> new ResourceNotFoundException("climber","id",climberId));
        ClimbingGroup climbingGroup = climbingGroupRepository.findById(climbingGroupId)
                .orElseThrow(()-> new ResourceNotFoundException("climbing group","id",climbingGroupId));

        climbingGroup.addClimber(climber);
        climbingGroupRepository.save(climbingGroup);
    }

    @Override
    public void deleteClimberFromGroup(Long climberId, Long climbingGroupId){
        Climber climber = climberRepository.findById(climberId)
                .orElseThrow(()-> new ResourceNotFoundException("climber","id",climberId));
        ClimbingGroup climbingGroup = climbingGroupRepository.findById(climbingGroupId)
                .orElseThrow(()-> new ResourceNotFoundException("climbing group","id",climbingGroupId));

        climbingGroup.removeClimber(climber);
        climbingGroupRepository.save(climbingGroup);
    }


    @Override
    public void deleteGroup(Long id) {
        Optional<ClimbingGroup> climbingGroup = climbingGroupRepository.findById(id);

        climbingGroup.ifPresent(climbingGroupRepository::delete);
    }

}
