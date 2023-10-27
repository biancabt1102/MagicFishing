package br.com.fiap.magicfishing.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Controller
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    UserRepository repository;

    @GetMapping
    public String index(Model model, @AuthenticationPrincipal OAuth2User user){
        model.addAttribute("avatar_url", user.getAttribute("avatar_url"));
        model.addAttribute("username", user.getAttribute("name"));
        model.addAttribute("users", repository.findAll());
        return "user/index";
    }
}
