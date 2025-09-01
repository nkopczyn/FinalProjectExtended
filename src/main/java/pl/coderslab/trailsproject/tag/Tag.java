package pl.coderslab.trailsproject.tag;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private String description;

    // mappedBy - wskazanie, że właścicielem relaji jest Trail
    @ManyToMany(mappedBy = "tags")
    private List<Trail> trails;
    // podczas serializacji tylko z jednej strony relacja będzie serializowana
}

