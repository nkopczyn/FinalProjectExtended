package pl.coderslab.trailsproject.trail;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.coderslab.trailsproject.point.Point;

// ta klasa odzwierciedla co będzie przesyłane postem
@Builder
@Getter
@Setter

public class TrailDTO {
    @NotBlank(message = "Trail name is required")
    private String trailName;

    @NotNull(message = "Trail start point is required")
    private Point startPoint;

    @NotNull(message = "Trail end point is required")
    private Point endPoint;

    @NotBlank(message = "Mountain range for trail needs to be specified")
    private String mountRangeName;

}