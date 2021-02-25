package me.calibri.webprojectspring.services;

import me.calibri.webprojectspring.entities.Note;
import me.calibri.webprojectspring.entities.Pictures;
import me.calibri.webprojectspring.entities.User;
import me.calibri.webprojectspring.models.NoteEditModel;
import me.calibri.webprojectspring.models.NoteShareModel;
import me.calibri.webprojectspring.repositories.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserService userService;

    public NoteService(NoteRepository noteRepository, UserService userService) {
        this.noteRepository = noteRepository;
        this.userService = userService;
    }

    public Note createNote(User owner, String title, String content, List<Pictures> pictures) {
        Note note = new Note();
        note.setOwner(owner);
        note.setTitle(title);
        note.setContent(content);
        note.setPictures(pictures); //NULL проблемът е решен в set метода
        note.setUsers(new ArrayList<>());
        return noteRepository.save(note);
    }
    public void updateNote(NoteEditModel model){
        Note note = getNoteById(model.getId());
        note.setTitle(model.getTitle());
        note.setContent(model.getContent());
        noteRepository.save(note);
    }

    public void deleteNoteById(long id) {
        noteRepository.deleteById(id);
    }

    public List<Note> findNotesByOwner(User owner) {
        Optional<List<Note>> notes = noteRepository.findAllByOwner(owner);
        return notes.orElse(null);
    }

    public boolean checkOwner(Note note,long userId){
        if(note.getOwner().getUserId() == userId) return true;
        else{
            throw new RuntimeException("You do not own access to this note!");
        }
    }
    public Note getNoteById(long id){
        Optional<Note> optionalNote = noteRepository.findById(id);
        if(optionalNote.isPresent()){
            return optionalNote.get();
        }else {
            throw new RuntimeException("No such note with this id");
        }
    }

    public void shareNote(NoteShareModel model){

        User user = userService.getUserByUsernameOrEmail(model.getUsername());
        Note note = getNoteById(model.getNoteId());
        note.getUsers().add(user);
        noteRepository.save(note);
    }


}
