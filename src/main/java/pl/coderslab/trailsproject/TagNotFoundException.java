package pl.coderslab.trailsproject;

public class TagNotFoundException extends RuntimeException {
    public TagNotFoundException(Long tagId) {
        super("Tag numer " + tagId + " nie został znaleziony");
    }
}
