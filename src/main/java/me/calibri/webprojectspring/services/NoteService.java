package me.calibri.webprojectspring.services;

import me.calibri.webprojectspring.entities.Note;
import me.calibri.webprojectspring.entities.Pictures;
import me.calibri.webprojectspring.entities.User;
import me.calibri.webprojectspring.repositories.NoteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NoteService {
    private final NoteRepository noteRepository;


    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Note createNote(User owner, String content, List<Pictures> pictures) {
        Note note = new Note();
        note.setOwner(owner);
        note.setContent(content);
        note.setPictures(pictures); //NULL проблемът е решен в set метода
        note.setUsers(new ArrayList<>());
        return noteRepository.save(note);
    }

    public void deleteNoteById(long id) {
        noteRepository.deleteById(id);
    }

    public List<Note> findNotesByOwner(User owner) {
        Optional<List<Note>> notes = noteRepository.findAllByOwner(owner);
        return notes.orElse(null);
    }
}
