package ua.goit.hw18restapi.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.goit.hw18restapi.model.Note;
import ua.goit.hw18restapi.model.dto.request.NoteUpdateRequest;
import ua.goit.hw18restapi.model.dto.response.NoteResponse;
import ua.goit.hw18restapi.repository.NoteRepository;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    NoteRepository noteRepository;

    @InjectMocks
    NoteService noteService;

    @Test
    void testNoteUpdatedSuccessfully() {
        Long noteId = 1L;
        NoteUpdateRequest noteUpdateRequest = NoteUpdateRequest.builder()
                .title("New title")
                .content("New content")
                .build();
        Note noteToUpdate = Note.builder()
                .title("Old title")
                .content("Old content")
                .build();
        when(noteRepository.findById(noteId)).thenReturn(Optional.of(noteToUpdate));
        when(noteRepository.save(noteToUpdate)).thenReturn(noteToUpdate);
        NoteResponse update = noteService.update(noteUpdateRequest, noteId);
        Assertions.assertNotNull(update);
        verify(noteRepository).findById(noteId);
        verify(noteRepository).save(noteToUpdate);
    }
}