package me.calibri.webprojectspring.controllers;

import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class TutorialController {
    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name",required = false,defaultValue = "World")String name, Model model,Session session){
        model.addAttribute("name", name);
        return "Greeting";
    }
}
