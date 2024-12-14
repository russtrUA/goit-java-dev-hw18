package ua.goit.hw18restapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.goit.hw18restapi.exception.EntityNotFoundException;
import ua.goit.hw18restapi.model.Note;
import ua.goit.hw18restapi.model.dto.request.NoteCreateRequest;
import ua.goit.hw18restapi.model.dto.request.NoteUpdateRequest;
import ua.goit.hw18restapi.model.dto.response.NoteResponse;
import ua.goit.hw18restapi.repository.NoteRepository;

import static ua.goit.hw18restapi.config.Constants.NOTE_WITH_ID_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserService userService;
    public Page<NoteResponse> listAll(PageRequest pageRequest) {
        return noteRepository.getNotesByUser(userService.findByUserName(getCurrentUserName()), pageRequest)
                .map(note -> NoteResponse.builder()
                        .content(note.getContent())
                        .title(note.getTitle())
                        .build());
    }

    public NoteResponse add(NoteCreateRequest note) {
        String username = getCurrentUserName();
        Note newNote = Note.builder()
                .content(note.content())
                .title(note.title())
                .user(userService.findByUserName(username))
                .build();
        Note save = noteRepository.save(newNote);
        return NoteResponse.builder()
                .title(save.getTitle())
                .content(save.getContent())
                .build();
    }

    private static String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public void deleteById(long id) {
        noteRepository.deleteById(id);
    }

    @Transactional
    public NoteResponse update(NoteUpdateRequest noteUpdateRequest, Long id) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format(NOTE_WITH_ID_NOT_FOUND, id)));
        note.setTitle(noteUpdateRequest.title());
        note.setContent(noteUpdateRequest.content());
        Note save = noteRepository.save(note);
        return NoteResponse.builder()
                .title(save.getTitle())
                .content(save.getContent())
                .build();
    }

    public Note getById(long id) {
       return noteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format(NOTE_WITH_ID_NOT_FOUND, id)));
    }

}
