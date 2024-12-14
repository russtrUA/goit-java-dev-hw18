package ua.goit.hw18restapi.model.dto.response;

import lombok.Builder;

@Builder
public record NoteResponse(String title, String content) {
}
