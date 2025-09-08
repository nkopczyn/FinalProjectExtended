package pl.coderslab.trailsproject;

public class NoTrailsAvailableException extends RuntimeException {
    public NoTrailsAvailableException() {
        super("No trails available for this tag");
    }
}
