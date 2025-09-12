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

    @NotBlank(message = "Szlak musi mieć nazwę")
    private String tagName;

    @Size(max = 100, message = "Opis nie może być dłuższy niż 100 znaków")
    private String tagDescription;

    private List<Long> tagTrailIds;
    // lista Id szlaków, które będą miały ten tag

}
