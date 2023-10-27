package br.com.fiap.magicfishing.fish;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import br.com.fiap.magicfishing.user.User;
import br.com.fiap.magicfishing.user.UserService;

@Service
public class FishService {

    @Autowired
    FishRepository repository;

    @Autowired
    UserService userService;

    public List<Fish> findAll(){
        return repository.findAll();
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

    public void create(Fish fish){
        repository.save(fish);
    }

    public void catchFish(Long id, User user) {

        var optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new RuntimeException("Peixe não encontrado");
        }

        var fish = optional.get();
        if (fish.getUser() != null) {
            throw new RuntimeException("Peixe já atribuído");
        }

        fish.setUser(user);

        var userCatchFish = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.addScore(User.convert(userCatchFish), fish.getScore());

        repository.save(fish);
    }

    public void dropFish(Long id, User user) {

        var optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new RuntimeException("Peixe não encontrado");
        }

        var fish = optional.get();

        if (fish.getUser() == null) {
            throw new RuntimeException("Peixe não foi atribuido");
        }

        if (!fish.getUser().equals(user)) {
            throw new RuntimeException("Peixe atribuído para outro usuário");
        }

        fish.setUser(null);
        repository.save(fish);
    }
}
