package com.lxisoft.redalert.service.mapper;

import com.lxisoft.redalert.domain.*;
import com.lxisoft.redalert.service.dto.FileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity File and its DTO FileDTO.
 */
@Mapper(componentModel = "spring", uses = {FeedMapper.class})
public interface FileMapper extends EntityMapper<FileDTO, File> {

    @Mapping(source = "feed.id", target = "feedId")
    FileDTO toDto(File file);

    @Mapping(source = "feedId", target = "feed")
    File toEntity(FileDTO fileDTO);

    default File fromId(Long id) {
        if (id == null) {
            return null;
        }
        File file = new File();
        file.setId(id);
        return file;
    }
}
