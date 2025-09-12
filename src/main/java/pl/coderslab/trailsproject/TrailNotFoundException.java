package pl.coderslab.trailsproject;

public class TrailNotFoundException extends RuntimeException {
    public TrailNotFoundException(Long trailId) {
        super("Szlak numer " + trailId + " nie został znaleziony");
    }
}
