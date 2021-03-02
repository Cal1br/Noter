package me.calibri.webprojectspring.services;

import me.calibri.webprojectspring.entities.Note;
import me.calibri.webprojectspring.entities.User;
import me.calibri.webprojectspring.models.NoteDto;
import me.calibri.webprojectspring.models.NoteEditModel;
import me.calibri.webprojectspring.models.NoteShareModel;
import me.calibri.webprojectspring.repositories.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserService userService;

    public NoteService(NoteRepository noteRepository, UserService userService) {
        this.noteRepository = noteRepository;
        this.userService = userService;
    }

    public Note createNote(User owner, String title, String content) {
        Note note = new Note();
        note.setOwner(owner);
        note.setTitle(title);
        note.setContent(content);
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

    public List<NoteDto> findNotesByOwner(User owner) {
        Optional<List<Note>> optionalNotes = noteRepository.findAllByOwner(owner);
        List<Note> notes = optionalNotes.orElse(new ArrayList<>());
        notes.addAll(owner.getSharedNotes());
        
        return notes
                .stream()
                .sorted(Comparator.comparing(Note::getCreateTimestamp).reversed())
                .map(note -> {
                    NoteDto noteDto = note.toDto();
                    noteDto.setUserId(owner.getUserId());
                    return noteDto;
                })
                .collect(Collectors.toList());
    }

    public void checkOwner(Note note,long userId){
        if (note.getOwner().getUserId() != userId) {
            throw new RuntimeException("You do not own access to this note!");
        }
    }

    public void canAccess(Note note, long userId){
        if (note.getOwner().getUserId() != userId) {
            if(note.getUsers().stream().anyMatch(user -> user.getUserId()==userId)){

            }
            else{
                throw new RuntimeException("You do not own access to this note!");
            }
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
