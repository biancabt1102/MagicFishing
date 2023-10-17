package br.com.fiap.magicfishing.fish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/fish")
public class FishController {

    @Autowired
    FishService service;
    
    @GetMapping
    public String index(Model model){
        model.addAttribute("fishs", service.findAll());
        return "fish/index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect){
        service.delete(id);
        redirect.addFlashAttribute("success", "Peixe apagado com sucesso");
        return "redirect:/fish";
    }
}
