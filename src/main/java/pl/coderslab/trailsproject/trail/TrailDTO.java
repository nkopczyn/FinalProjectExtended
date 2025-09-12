package pl.coderslab.trailsproject.trail;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.coderslab.trailsproject.point.PointDTO;

@Builder
@Getter
@Setter

public class TrailDTO {
    private Long id;

    @NotBlank(message = "Szlak musi mieć nazwę")
    private String trailName;

    @Valid
    @NotNull(message = "Wpisz punkt początkowy")
    private PointDTO startPoint;

    @Valid
    @NotNull(message = "Wpisz punkt końcowy")
    private PointDTO endPoint;

    @NotBlank(message = "Określ pasmo górskie")
    private String mountRangeName;


    public TrailDTO() {
    }

    public TrailDTO(Long id, String trailName, PointDTO startPoint, PointDTO endPoint, String mountRangeName) {
        this.id = id;
        this.trailName = trailName;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.mountRangeName = mountRangeName;
    }

    public static TrailDTO convertToDTO(Trail trail) {
        TrailDTO trailDTO = new TrailDTO();
        trailDTO.setId(trail.getId());
        trailDTO.setTrailName(trail.getName());
        trailDTO.setMountRangeName(trail.getMountRange().getName());
        trailDTO.setStartPoint(new PointDTO(trail.getStart().getLatitude(), trail.getStart().getLongitude()));
        trailDTO.setEndPoint(new PointDTO(trail.getFinish().getLatitude(), trail.getFinish().getLongitude()));

        return trailDTO;
    }
}