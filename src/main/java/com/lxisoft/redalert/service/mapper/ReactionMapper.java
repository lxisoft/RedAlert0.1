package com.lxisoft.redalert.service.mapper;

import com.lxisoft.redalert.domain.*;
import com.lxisoft.redalert.service.dto.ReactionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Reaction and its DTO ReactionDTO.
 */
@Mapper(componentModel = "spring", uses = {FeedMapper.class, ActionMapper.class})
public interface ReactionMapper extends EntityMapper<ReactionDTO, Reaction> {

    @Mapping(source = "feed.id", target = "feedId")
    @Mapping(source = "action.id", target = "actionId")
    ReactionDTO toDto(Reaction reaction);

    @Mapping(source = "feedId", target = "feed")
    @Mapping(source = "actionId", target = "action")
    Reaction toEntity(ReactionDTO reactionDTO);

    default Reaction fromId(Long id) {
        if (id == null) {
            return null;
        }
        Reaction reaction = new Reaction();
        reaction.setId(id);
        return reaction;
    }
}
