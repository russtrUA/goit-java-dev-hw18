package ua.goit.hw18restapi.model.dto.request;

import lombok.Builder;

@Builder
public record NoteCreateRequest(String title, String content) {
}