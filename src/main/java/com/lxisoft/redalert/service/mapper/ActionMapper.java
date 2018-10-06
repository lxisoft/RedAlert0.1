package com.lxisoft.redalert.service.mapper;

import com.lxisoft.redalert.domain.*;
import com.lxisoft.redalert.service.dto.ActionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Action and its DTO ActionDTO.
 */
@Mapper(componentModel = "spring", uses = {UserFeedMapper.class})
public interface ActionMapper extends EntityMapper<ActionDTO, Action> {

    @Mapping(source = "user.id", target = "userId")
    ActionDTO toDto(Action action);

    @Mapping(target = "scopeOfActions", ignore = true)
    @Mapping(source = "userId", target = "user")
    Action toEntity(ActionDTO actionDTO);

    default Action fromId(Long id) {
        if (id == null) {
            return null;
        }
        Action action = new Action();
        action.setId(id);
        return action;
    }
}
