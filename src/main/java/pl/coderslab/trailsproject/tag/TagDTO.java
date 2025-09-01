package pl.coderslab.trailsproject.tag;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Builder
@Getter
@Setter
public class TagDTO {
    private String tagName;
    private String tagDescription;
    private List<Long> tagTrailIds;
    // Id's of trails that will have this tag
}
