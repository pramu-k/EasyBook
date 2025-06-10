package com.pramu_k.easybook.controller;

import com.pramu_k.easybook.model.User;
import com.pramu_k.easybook.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    private UserService userService;

    public AuthController(UserService userService) {
        super();
        this.userService = userService;
    }
    @ModelAttribute("user")
    public User userRegistrationDto() {
        return new User();
    }

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout) {
        if (error != null) {
        System.out.println("Login failed: Invalid credentials.");
        }

        if (logout != null) {
            System.out.println("User has logged out.");
        }
        return "login"; // This should match login.html in src/main/resources/templates
    }
    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }
    @PostMapping("/register")
    public String registerUserAccount(@ModelAttribute("user")
                                      User registrationDto) {

        try {
            userService.save(registrationDto);
        }catch(Exception e)
        {
            System.out.println(e);
            return "redirect:/register?email_invalid";
        }
        return "redirect:/register?success";
    }
}
