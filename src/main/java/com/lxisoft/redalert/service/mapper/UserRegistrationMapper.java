package com.lxisoft.redalert.service.mapper;

import com.lxisoft.redalert.domain.*;
import com.lxisoft.redalert.service.dto.UserRegistrationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserRegistration and its DTO UserRegistrationDTO.
 */
@Mapper(componentModel = "spring", uses = {FriendsMapper.class})
public interface UserRegistrationMapper extends EntityMapper<UserRegistrationDTO, UserRegistration> {


    @Mapping(target = "messages", ignore = true)
    UserRegistration toEntity(UserRegistrationDTO userRegistrationDTO);

    default UserRegistration fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserRegistration userRegistration = new UserRegistration();
        userRegistration.setId(id);
        return userRegistration;
    }
}
