package pl.coderslab.trailsproject.trail;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.coderslab.trailsproject.point.Point;

// ta klasa odzwierciedla co będzie przesyłane postem
@Builder
@Getter
@Setter

public class TrailDTO {
    private String trailName;
    private Point startPoint;
    private Point endPoint;
    private String mountRangeName;

}