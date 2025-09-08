package pl.coderslab.trailsproject.tag;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO {
    private String tagName;

    @Size(max = 100, message = "Description cannot be longer than 100 characters")
    private String tagDescription;

    @Size(min = 1, message = "At least one trail must have this tag")
    private List<Long> tagTrailIds;
    // list of Id's of trails that will have this tag

}
