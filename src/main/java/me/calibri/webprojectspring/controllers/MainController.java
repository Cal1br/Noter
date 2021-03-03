package me.calibri.webprojectspring.controllers;

import me.calibri.webprojectspring.entities.Note;
import me.calibri.webprojectspring.entities.User;
import me.calibri.webprojectspring.models.*;
import me.calibri.webprojectspring.services.NoteService;
import me.calibri.webprojectspring.services.UserService;
import me.calibri.webprojectspring.utils.LoginUtility;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;


@Controller
@RequestMapping
public class MainController {

    private final String HOME_PAGE = "redirect:/";

    private final UserService userService;
    private final NoteService noteService;

    public MainController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @GetMapping("/")
    public String landing(final HttpSession session) {
        if (LoginUtility.isLoggedIn(session)) {
            return "Home";
        }
        return "Login";
    }


    @PostMapping("/")
    public String login(HttpSession session, Model model, @RequestParam String username, @RequestParam String password) {

        try {
            LoginUtility.logIn(session, userService.login(username, password).toDto());
            return "Home";
        } catch (RuntimeException exception) {
            model.addAttribute("errmessage", exception.getMessage());
        }
        return "Login";
    }

    @GetMapping("/register")
    public String getRegister(HttpSession session) {
        if (LoginUtility.isLoggedIn(session)) {
            return HOME_PAGE;
        }
        return "Register";
    }

    @PostMapping("register")
    public String register(HttpSession session, Model model, @RequestParam String username, @RequestParam String password, @RequestParam String email) {

        try {
            LoginUtility.logIn(session, userService.register(username, password, email).toDto());
            return "redirect:/";
        } catch (RuntimeException exception) {
            model.addAttribute("errmessage", exception.getMessage());
        }
        return "Register";
    }

    @GetMapping("/home")
    public String getHome(final HttpSession session) {
        if (!LoginUtility.isLoggedIn(session)) return HOME_PAGE;
        return "Home";
    }

    @GetMapping("/noteCreator")
    public String getNoteCreator(HttpSession session) {
        if (!LoginUtility.isLoggedIn(session)) return HOME_PAGE;

        return "NoteCreator";
    }

    @PostMapping("noteCreator")
    public String postNote(HttpSession session, @RequestParam String title, @RequestParam String content,Model model) {
        try {
            User owner = userService.getUserById(LoginUtility.getUserDto(session).getId());
            noteService.createNote(owner, title, content);
            return "redirect:/notes";
        } catch (RuntimeException exception) {
            model.addAttribute("errmessage", exception.getMessage());
        }
        return "NoteCreator";
    }

    @GetMapping("/notes")
    public String getNotes(Model model, HttpSession session) {
        if (!LoginUtility.isLoggedIn(session)) return HOME_PAGE;

        UserDto userDto = LoginUtility.getUserDto(session);
        User userEntity = userService.getUserById(userDto.getId());
        model.addAttribute("notes", noteService.findNotesByOwner(userEntity));
        return "Notes";
    }

    @GetMapping("/noteviewer/{id}")
    public String viewNoteById(Model model, HttpSession session, @PathVariable long id) {
        if (!LoginUtility.isLoggedIn(session)) return HOME_PAGE;
        try {
            Note note = noteService.getNoteById(id);
            long userId = LoginUtility.getUserDto(session).getId();
            noteService.canAccess(note, userId);
            NoteDto noteDto = note.toDto();
            noteDto.setUserId(userId);
            model.addAttribute("note", noteDto);
            return "NoteView";
        } catch (RuntimeException exception) {
            model.addAttribute("errmessage", exception.getMessage());
        }
        return "redirect:/notes";
    }

    @PostMapping("/savenote")
    public @ResponseBody
    ResponseEntity<String> saveNote(@RequestBody NoteEditModel model,HttpSession session){
        if (!LoginUtility.isLoggedIn(session)) return ResponseEntity.badRequest().build();
        noteService.updateNote(model);
        return ResponseEntity.status(200).build();
    }
    @PostMapping("/sharenote")
    public @ResponseBody
    ResponseEntity<String> shareNote(@RequestBody NoteShareModel model, HttpSession session, Model page){
        if (!LoginUtility.isLoggedIn(session)) return ResponseEntity.badRequest().build();
        Note note = noteService.getNoteById(model.getNoteId());

        try{
            noteService.checkOwner(note, LoginUtility.getUserDto(session).getId());
            noteService.shareNote(model);
        }
        catch(RuntimeException exception){
            return ResponseEntity.status(500).body(exception.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/logout")
    public String logOut(final HttpSession session) {
        LoginUtility.logOut(session);
        return HOME_PAGE;
    }
    @PostMapping("/deletenote")
    public @ResponseBody
    ResponseEntity<String> deleteNote(long id,HttpSession session){
        if (!LoginUtility.isLoggedIn(session)) return ResponseEntity.badRequest().build();
        Note note = noteService.getNoteById(id);
        try{
            noteService.checkOwner(note,LoginUtility.getUserDto(session).getId());
            noteService.deleteNoteById(id);
        }
        catch (RuntimeException exception){
            return ResponseEntity.status(500).body(exception.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/about")
    public String about(){
        return "About";
    }
}
