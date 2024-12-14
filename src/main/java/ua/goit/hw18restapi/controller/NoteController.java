package ua.goit.hw18restapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.goit.hw18restapi.model.Note;
import ua.goit.hw18restapi.model.dto.request.NoteCreateRequest;
import ua.goit.hw18restapi.model.dto.request.NoteUpdateRequest;
import ua.goit.hw18restapi.model.dto.response.NoteResponse;
import ua.goit.hw18restapi.service.NoteService;


@RestController
@RequestMapping("/api/v1/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @GetMapping()
    public Page<NoteResponse> findAllNotes(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return noteService.listAll(pageRequest);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NoteResponse createNote(@RequestBody NoteCreateRequest request) {
        return noteService.add(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("@securityService.isNoteCreatedByPrincipal(#id)")
    public void deleteNoteById(@PathVariable Long id) {
        noteService.deleteById(id);
    }
    @PutMapping(value ="/{id}")
    @PreAuthorize("@securityService.isNoteCreatedByPrincipal(#id)")
    public NoteResponse editNoteById(@PathVariable Long id,
                                     @RequestBody NoteUpdateRequest noteUpdateRequest) {
        return noteService.update(noteUpdateRequest, id);
    }
    @GetMapping("/{id}")
    @PreAuthorize("@securityService.isNoteCreatedByPrincipal(#id)")
    public NoteResponse getNoteById(@PathVariable Long id) {
        Note byId = noteService.getById(id);
        return NoteResponse.builder()
                .title(byId.getTitle())
                .content(byId.getContent())
                .build();
    }
}
