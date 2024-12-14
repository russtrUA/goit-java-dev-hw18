package ua.goit.hw18restapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.goit.hw18restapi.exception.EntityNotFoundException;
import ua.goit.hw18restapi.repository.NoteRepository;
import ua.goit.hw18restapi.repository.UserRepository;
import ua.goit.hw18restapi.security.CustomUserDetails;

import static ua.goit.hw18restapi.config.Constants.NOTE_WITH_ID_NOT_FOUND;
import static ua.goit.hw18restapi.config.Constants.USER_NAME_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    public boolean isNoteCreatedByPrincipal(Long noteId) {
        return getCurrentUser().getId().equals(noteRepository.findById(noteId).orElseThrow(
                () -> new EntityNotFoundException(String.format(NOTE_WITH_ID_NOT_FOUND, noteId))
        ).getUser().getId());
    }
    
    private CustomUserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        Long id = userRepository.findByUserName(principal.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(String.format(USER_NAME_NOT_FOUND, principal.getUsername())))
                .getId();
        return CustomUserDetails.builder()
                .username(principal.getUsername())
                .password(principal.getPassword())
                .authorities(principal.getAuthorities())
                .id(id)
                .build();
    }
}
