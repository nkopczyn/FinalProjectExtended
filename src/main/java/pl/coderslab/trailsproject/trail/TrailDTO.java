package pl.coderslab.trailsproject.trail;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.coderslab.trailsproject.point.PointDTO;

// ta klasa odzwierciedla co będzie przesyłane postem
@Builder
@Getter
@Setter

public class TrailDTO {
    @NotBlank(message = "Trail name is required")
    private String trailName;

    @NotNull(message = "Trail start point is required")
    private PointDTO startPoint;

    @NotNull(message = "Trail end point is required")
    private PointDTO endPoint;

    @NotBlank(message = "Mountain range for trail needs to be specified")
    private String mountRangeName;


    public TrailDTO() {
    }
    public TrailDTO(String trailName, PointDTO startPoint, PointDTO endPoint, String mountRangeName) {
        this.trailName = trailName;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.mountRangeName = mountRangeName;
    }
}