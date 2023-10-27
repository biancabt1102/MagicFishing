package br.com.fiap.magicfishing.fish;

import br.com.fiap.magicfishing.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Fish {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Size(min = 10, message = "{fish.description.size}")
    private String description;
    
    @Size(min = 10, message = "{fish.size}")
    private String size;

    @Positive
    private Integer score;

    @Min(0) @Max(100)
    private Integer status;

    @ManyToOne
    private User user;
}
