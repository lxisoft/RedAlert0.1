package com.lxisoft.redalert.service.mapper;

import com.lxisoft.redalert.domain.*;
import com.lxisoft.redalert.service.dto.FeedDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Feed and its DTO FeedDTO.
 */
@Mapper(componentModel = "spring", uses = {ContactMapper.class, UserRegistrationMapper.class})
public interface FeedMapper extends EntityMapper<FeedDTO, Feed> {

    @Mapping(source = "userRegistration.id", target = "userRegistrationId")
    FeedDTO toDto(Feed feed);

    @Mapping(target = "actions", ignore = true)
    @Mapping(target = "scopeOfFeeds", ignore = true)
    @Mapping(target = "files", ignore = true)
    @Mapping(source = "userRegistrationId", target = "userRegistration")
    Feed toEntity(FeedDTO feedDTO);

    default Feed fromId(Long id) {
        if (id == null) {
            return null;
        }
        Feed feed = new Feed();
        feed.setId(id);
        return feed;
    }
}
