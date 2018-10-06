package com.lxisoft.redalert.service.mapper;

import com.lxisoft.redalert.domain.*;
import com.lxisoft.redalert.service.dto.FriendsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Friends and its DTO FriendsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FriendsMapper extends EntityMapper<FriendsDTO, Friends> {


    @Mapping(target = "users", ignore = true)
    Friends toEntity(FriendsDTO friendsDTO);

    default Friends fromId(Long id) {
        if (id == null) {
            return null;
        }
        Friends friends = new Friends();
        friends.setId(id);
        return friends;
    }
}
