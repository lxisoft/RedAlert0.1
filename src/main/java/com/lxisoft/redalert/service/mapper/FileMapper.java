package com.lxisoft.redalert.service.mapper;

import com.lxisoft.redalert.domain.*;
import com.lxisoft.redalert.service.dto.FileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity File and its DTO FileDTO.
 */
@Mapper(componentModel = "spring", uses = {UserFeedMapper.class})
public interface FileMapper extends EntityMapper<FileDTO, File> {

    @Mapping(source = "userFeed.id", target = "userFeedId")
    FileDTO toDto(File file);

    @Mapping(source = "userFeedId", target = "userFeed")
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
