package br.com.fiap.magicfishing.fish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fiap.magicfishing.user.User;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/fish")
public class FishController {

    @Autowired
    FishService service;

    @Autowired
    MessageSource message;
    
    @GetMapping
    public String index(Model model, @AuthenticationPrincipal OAuth2User user){
        model.addAttribute("avatar_url", user.getAttribute("avatar_url"));
        model.addAttribute("username", user.getAttribute("name"));
        model.addAttribute("fishs", service.findAll());
        return "fish/index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect){
        service.delete(id);
        redirect.addFlashAttribute("success", message.getMessage("fish.delete.success", null, LocaleContextHolder.getLocale()));
        return "redirect:/fish";
    }

    @GetMapping("new")
    public String form(Fish fish, Model model, @AuthenticationPrincipal OAuth2User user){
        model.addAttribute("avatar_url", user.getAttribute("avatar_url"));
        model.addAttribute("username", user.getAttribute("name"));
        return "fish/form";
    }

    @PostMapping
    public String create(@Valid Fish fish, BindingResult result , RedirectAttributes redirect){
        if (result.hasErrors()) return "fish/form";
        service.create(fish);
        redirect.addFlashAttribute("success", message.getMessage("fish.create.success", null, LocaleContextHolder.getLocale()));
        return "redirect:/fish";
    }

    @GetMapping("/catch/{id}")
    public String catchFish(@PathVariable Long id, @AuthenticationPrincipal OAuth2User user){
        service.catchFish(id, User.convert(user));
        return "redirect:/fish";
    }

    @GetMapping("/drop/{id}")
    public String dropFish(@PathVariable Long id, @AuthenticationPrincipal OAuth2User user){
        service.dropFish(id, User.convert(user));
        return "redirect:/fish";
    }
}
