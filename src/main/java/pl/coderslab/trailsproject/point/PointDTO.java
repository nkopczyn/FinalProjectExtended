package pl.coderslab.trailsproject.point;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointDTO {
    private double latitude;
    private double longitude;

    public PointDTO(@NotNull(message = "Latitude is required")
                    @DecimalMin(value = "-90.0", message = "Latitude value has to be >= -90")
                    @DecimalMax(value = "90.0", message = "Latitude value has to be <= 90")
                    Double latitude,

                    @NotNull(message = "Longitude is required")
                    @DecimalMin(value = "-180.0", message = "Longitude value has to be >= -180")
                    @DecimalMax(value = "180.0", message = "Longitude value has to be <= 180")
                    Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public PointDTO() {
    }
}

