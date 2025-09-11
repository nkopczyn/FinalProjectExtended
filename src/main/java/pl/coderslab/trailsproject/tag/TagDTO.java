package pl.coderslab.trailsproject.tag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO {

    @NotBlank(message = "Tag name cannot be empty")
    private String tagName;

    @Size(max = 100, message = "Description cannot be longer than 100 characters")
    private String tagDescription;

    private List<Long> tagTrailIds;
    // lista Id szlaków, które będą miały ten tag

}
