package me.calibri.webprojectspring.repositories;

import me.calibri.webprojectspring.entities.Note;
import me.calibri.webprojectspring.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends CrudRepository<Note, Long> {
    Optional<Note> findByOwnerOrNoteId(final User owner, final Long noteId);
    Optional<List<Note>> findAllByOwner(final User owner);
}
