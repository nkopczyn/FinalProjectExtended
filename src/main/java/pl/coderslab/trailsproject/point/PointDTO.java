package pl.coderslab.trailsproject.point;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointDTO {
    @NotNull(message = "Podaj szerokość geograficzną")
    @DecimalMin(value = "-90.0", message = "Szerokość geograficzna musi być większa niż -90")
    @DecimalMax(value = "90.0", message = "Szerokość geograficzna być mniejsza niż 90")
    private double latitude;

    @NotNull(message = "Podaj długość geograficzną")
    @DecimalMin(value = "-180.0", message = "Długość geograficzna musi być większa niż -180")
    @DecimalMax(value = "180.0", message = "Długość geograficzna musi być mniejsza niż 180")
    private double longitude;

    public PointDTO(Double latitude,
                    Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public PointDTO() {
    }
}

