package pl.coderslab.trailsproject.point;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointDTO {
    @NotNull(message = "Latitude is required")
    @DecimalMin(value = "-90.0", message = "Latitude value has to be >= -90")
    @DecimalMax(value = "90.0", message = "Latitude value has to be <= 90")
    private double latitude;

    @NotNull(message = "Longitude is required")
    @DecimalMin(value = "-180.0", message = "Longitude value has to be >= -180")
    @DecimalMax(value = "180.0", message = "Longitude value has to be <= 180")
    private double longitude;

    public PointDTO(Double latitude,
                    Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public PointDTO() {
    }
}

