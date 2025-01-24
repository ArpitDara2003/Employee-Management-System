package com.example.project_1.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.project_1.dataModels.Company;
import com.example.project_1.dataModels.UI.NavItem;

@Controller
public class HomeController {
    private boolean isLoggedIn = false;
    private boolean isAdmin = false;
    private long CompanyId = 1;


    @GetMapping({"/", "/home"})
    public String home(Model model) {
        model.addAttribute("company", "SiteMachine");

        List<NavItem> navItems = Arrays.asList(
                new NavItem("Home", "home"),
                new NavItem("About", "about"),
                new NavItem("Settings", "settings")
        );

        model.addAttribute("navItems", navItems);


        model.addAttribute("news", news);
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
        return "settings";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("companyId", CompanyId);
        return "login";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam("username") String username, 
                              @RequestParam("password") String password, 
                              @RequestParam("companyId") long companyId, 
                              Model model) {
        // Dummy authentication logic
        if () {
            isLoggedIn = true;
            model.addAttribute("message", "Login successful!");
            return "redirect:/home";
        } else {
            model.addAttribute("message", "Invalid credentials. Please try again.");
            return "login";
        }
    }


}

