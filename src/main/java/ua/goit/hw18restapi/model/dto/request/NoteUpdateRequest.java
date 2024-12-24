package ua.goit.hw18restapi.model.dto.request;

import lombok.Builder;

@Builder
public record NoteUpdateRequest(String title, String content) {
}

