package pl.coderslab.trailsproject;

public class TrailNotFoundException extends RuntimeException {
    public TrailNotFoundException(Long trailId) {
        super("Trail number " + trailId + " not found :( ");
    }
}
