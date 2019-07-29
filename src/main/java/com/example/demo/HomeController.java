package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    MessageRepo messageRepo;

    //to show list
    @RequestMapping("/")
    public String index(Principal principal, Model model){
        model.addAttribute("messages", messageRepo.findAll());
        model.addAttribute("user", userRepository.findAll());

        if(userService.getUser() != null) {
            model.addAttribute("user_id", userService.getUser().getId());

        }
        return "list";
    }

    //add message
    @GetMapping("/add")
    public String addMsg(Model model){
        model.addAttribute("message", new Message());
        return "msgform";
    }

    //Validate and save
    @PostMapping("/process")
    public String processForm(@Valid Message message, @ModelAttribute("user") User user, BindingResult result){
        if (result.hasErrors()){
            return "msgform";
        }
        message.setUser(userService.getUser());
        messageRepo.save(message);
        Set<Message> messages = new HashSet<>();
        messages.add(message);
        user.setMessages(messages);
        return "redirect:/";
    }

    @RequestMapping("/showmsg/{id}")
    public String showMsg(@PathVariable("id") long id, Model model){
        model.addAttribute("message", messageRepo.findById(id).get());
        return "showmsg";

    }

    @RequestMapping("/edit/{id}")
    public String editMessage(@PathVariable("id") long id, Model model){
        model.addAttribute("message", messageRepo.findById(id).get());
        return "msgform";
    }

//
//    SECURITY
//

    @GetMapping(value="/register")
    public String showRegistration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping(value="/register")
    public String processRegistrationPage(@Valid @ModelAttribute("user") User user, BindingResult result, Model model){
        model.addAttribute("user", user);
        if (result.hasErrors()) {
            return "registration";
        }
        else{
            userService.saveUser(user);model.addAttribute("message","User Account Created");
        }
        return "login";
    }




    @Autowired
    UserRepository userRepository;

//       @RequestMapping("/admin")
//    public String admin(){
//        return "admin";
//    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/secure")
    public String secure(Principal principal, Model model){
        String username = principal.getName();
        model.addAttribute("user", userRepository.findByUsername(username));
        return "list";
    }

}
