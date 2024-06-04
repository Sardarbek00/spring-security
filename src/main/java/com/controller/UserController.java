package com.controller;

import com.model.User;
import com.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserController {
UserService userService;

    @GetMapping
    public String mainPage(){
        return "main-page";
    }
@GetMapping("/add")
    public String addUser(Model model){
        model.addAttribute("user",new User());
        return "user/save";
    }
@PostMapping("/save")
    public String saveUser(@ModelAttribute("user")User user){
        userService.save(user);
        return "redirect:/find-all";
    }
@GetMapping("/find-all")
    public String findAll(Model model){
        model.addAttribute("users",userService.findAll());
        return "user/get-all";
    }
@GetMapping("/update/{id}")
    public String updateUser(@PathVariable("id")Long id,Model model){
        model.addAttribute("user",userService.findById(id));
        return "user/update";
    }
@PostMapping("/update-save/{id}")
    public String updateSaveUser(@ModelAttribute("user")User user,@PathVariable("id")Long id){
        userService.update(id,user);
        return "redirect:/find-all";
    }
@GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id")Long id){
        userService.delete(id);
        return "redirect:/find-all";
    }
}
