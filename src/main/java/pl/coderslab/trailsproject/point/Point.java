package pl.coderslab.trailsproject.point;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Latitude is required")
    @DecimalMin(value = "-90.0", message = "Latitude value has to be >= -90")
    @DecimalMax(value = "90.0", message = "Latitude value has to be <= 90")
    private Double latitude;

    @NotNull(message = "Longitude is required")
    @DecimalMin(value = "-180.0", message = "Longitude value has to be >= -180")
    @DecimalMax(value = "180.0", message = "Longitude value has to be <= 180")
    private Double longitude;
}