package pl.coderslab.trailsproject.trail;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.coderslab.trailsproject.category.Category;
import pl.coderslab.trailsproject.mountrange.MountRange;
import pl.coderslab.trailsproject.point.Point;
import pl.coderslab.trailsproject.tag.Tag;

import java.util.List;


@Entity
@Getter
@Setter
public class Trail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private Point start;
    @ManyToOne
    private Point finish;

    private double length;

    // wiele trails może mieć tą samą category
    @ManyToOne
    private Category category;

    @ManyToOne
    private MountRange mountRange;

    @ManyToMany
    @JsonIgnore
    private List<Tag> tags;

}
