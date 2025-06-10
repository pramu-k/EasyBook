package com.pramu_k.easybook.controller;

import com.pramu_k.easybook.model.User;
import com.pramu_k.easybook.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class HomeController {
    private final UserRepository userRepository;
    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Welcome to My First Thymeleaf App!");
        model.addAttribute("users", userRepository.findAll());
        return "home";
    }
    @PostMapping("/users/update-email")
    public String updateUserEmail( @RequestParam String email,@RequestParam String password) {
        User optionalUser = userRepository.findByEmail(email);
        if (optionalUser!=null) {
            //User user = optionalUser.get();
            optionalUser.setPassword(password);
            userRepository.save(optionalUser);  // This performs the update
        }
        return "redirect:/";  // Or return a success view
    }

}
