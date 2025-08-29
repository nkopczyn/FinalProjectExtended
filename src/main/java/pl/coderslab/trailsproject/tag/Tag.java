package pl.coderslab.trailsproject.tag;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.coderslab.trailsproject.trail.Trail;

import java.util.List;

@Entity
@Getter
@Setter
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    // mappedBy - wskazanie, że właścicielem relaji jest Trail
    @ManyToMany(mappedBy = "tags")
    private List<Trail> trails;
}

