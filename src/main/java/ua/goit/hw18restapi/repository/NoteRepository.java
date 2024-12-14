package ua.goit.hw18restapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.goit.hw18restapi.model.Note;
import ua.goit.hw18restapi.model.User;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    Page<Note> getNotesByUser(User user, Pageable pageable);
}