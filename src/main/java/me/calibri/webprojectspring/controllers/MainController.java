package me.calibri.webprojectspring.controllers;

import me.calibri.webprojectspring.entities.User;
import me.calibri.webprojectspring.services.NoteService;
import me.calibri.webprojectspring.services.UserService;
import me.calibri.webprojectspring.utils.LoginUtility;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String postNote(HttpSession session, @RequestParam String title, @RequestParam String content) { //TODO Tuka sushto kartinkite
        try {
            User owner = userService.getUserById(LoginUtility.getUserDto(session).getId());
            noteService.createNote(owner, title, content, new ArrayList<>()); //TODO Dobavi Kartinkite.....
            return "redirect:/notes";
        } catch (RuntimeException exception) {
            //TODO SHOW ERROR MESSAGE
        }
        return "NoteCreator";
    }

    @GetMapping("/notes")
    public String getNotes(HttpSession session) {
        if (!LoginUtility.isLoggedIn(session)) return HOME_PAGE;
        return "Notes";
    }

    @GetMapping("/logout")
    public String logOut(final HttpSession session) {
        LoginUtility.logOut(session);
        return HOME_PAGE;
    }

    /*@GetMapping
    public String landing(Model model) {
        model.addAttribute("name", "calibri");
        model.addAttribute("pass", "Devin900");
        return "Login";
    }

    @GetMapping("home")
    public String home(Model model, HttpSession session) {
        Integer count = (Integer) session.getAttribute("count");

        if (count == null) {
            count = 0;
        }
        session.setAttribute("count", ++count);
        if (count == 10) {
            session.invalidate();
        }
        model.addAttribute("name", "calibri " + count + " " + session.getId());
        return "Home";
    }*/
}
