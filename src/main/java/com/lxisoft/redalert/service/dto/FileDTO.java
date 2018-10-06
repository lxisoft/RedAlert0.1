package com.lxisoft.redalert.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the File entity.
 */
public class FileDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] attachments;
    private String attachmentsContentType;

    private Long userFeedId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getAttachments() {
        return attachments;
    }

    public void setAttachments(byte[] attachments) {
        this.attachments = attachments;
    }

    public String getAttachmentsContentType() {
        return attachmentsContentType;
    }

    public void setAttachmentsContentType(String attachmentsContentType) {
        this.attachmentsContentType = attachmentsContentType;
    }

    public Long getUserFeedId() {
        return userFeedId;
    }

    public void setUserFeedId(Long userFeedId) {
        this.userFeedId = userFeedId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FileDTO fileDTO = (FileDTO) o;
        if (fileDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fileDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FileDTO{" +
            "id=" + getId() +
            ", attachments='" + getAttachments() + "'" +
            ", userFeed=" + getUserFeedId() +
            "}";
    }
}
