package pl.coderslab.trailsproject;

public class TagNotFoundException extends RuntimeException {
    public TagNotFoundException(Long tagId) {
        super("Tag number " + tagId + " not found :( ");
    }
}
