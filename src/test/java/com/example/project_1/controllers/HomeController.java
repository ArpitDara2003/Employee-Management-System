package com.example.project_1.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "This is a test!");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("message", "About Us Page");
        return "about";
    }

    @GetMapping("/settings")
    public String settings(Model model) {
        model.addAttribute("message", "Settings Page");
        return "about";
    }
}

