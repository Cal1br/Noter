package me.calibri.webprojectspring.controllers;

import me.calibri.webprojectspring.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping
public class MainController {


    private final UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String landing(){
        return "Login";
    }
    @PostMapping("/")
    public String login(Model model, @RequestParam String username, @RequestParam String password){

        try{
            userService.login(username,password);
            return "Home";
        }catch (RuntimeException exception){
            model.addAttribute("errmessage",exception.getMessage());
        }
        return "Login";
    }

    @GetMapping("/register")
    public String getRegister(){
        return "Register";
    }

    @PostMapping("register")
    public String register(Model model,@RequestParam String username, @RequestParam String password, @RequestParam String email){

        try {
            userService.register(username,password,email);
            return "Home";
        }catch (RuntimeException exception){
            model.addAttribute("errmessage",exception.getMessage());
        }
        return "Register";
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
