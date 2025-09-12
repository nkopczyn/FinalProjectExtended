package pl.coderslab.trailsproject;

public class NoTrailsAvailableException extends RuntimeException {
    public NoTrailsAvailableException() {
        super("Nie znaleziony szlaków dla tego tagu");
    }
}
