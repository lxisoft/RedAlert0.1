package com.lxisoft.redalert.service.mapper;

import com.lxisoft.redalert.domain.*;
import com.lxisoft.redalert.service.dto.UserFeedDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserFeed and its DTO UserFeedDTO.
 */
@Mapper(componentModel = "spring", uses = {FriendsMapper.class, UserRegistrationMapper.class})
public interface UserFeedMapper extends EntityMapper<UserFeedDTO, UserFeed> {

    @Mapping(source = "user.id", target = "userId")
    UserFeedDTO toDto(UserFeed userFeed);

    @Mapping(target = "actions", ignore = true)
    @Mapping(target = "scopeOfFeeds", ignore = true)
    @Mapping(target = "files", ignore = true)
    @Mapping(source = "userId", target = "user")
    UserFeed toEntity(UserFeedDTO userFeedDTO);

    default UserFeed fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserFeed userFeed = new UserFeed();
        userFeed.setId(id);
        return userFeed;
    }
}
