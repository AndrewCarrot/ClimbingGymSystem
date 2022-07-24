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

    @Override
    public void createGroup(ClimbingGroupDTO climbingGroupDTO) {
        Optional<ClimbingGroup> x = climbingGroupRepository.findClimbingGroupByName(climbingGroupDTO.getName());

        if (x.isPresent()){
            throw new RuntimeException("name already taken");
        }
        ClimbingGroup climbingGroup = modelMapper.map(climbingGroupDTO,ClimbingGroup.class);
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
        Optional<ClimbingGroup> x = climbingGroupRepository.findClimbingGroupByName(climbingGroupDTO.getName());

        if(x.isPresent())
            throw new RuntimeException("name already taken");

        climbingGroupRepository.findById(id).map(
                group -> {
                    group.setName(climbingGroupDTO.getName());
                    group.setCoach(climbingGroupDTO.getCoach());
                    group.setDayOfWeek(climbingGroupDTO.getDayOfWeek());
                    group.setClassTime(climbingGroupDTO.getClassTime());
                    return climbingGroupRepository.save(group);
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
