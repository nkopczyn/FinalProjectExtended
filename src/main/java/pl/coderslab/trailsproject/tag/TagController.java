package pl.coderslab.trailsproject.tag;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.trailsproject.TagNotFoundException;
import pl.coderslab.trailsproject.TrailNotFoundException;
import pl.coderslab.trailsproject.trail.Trail;
import pl.coderslab.trailsproject.trail.TrailService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;
    private final TrailService trailService;

    public TagController(TagService tagService, TrailService trailService) {
        this.tagService = tagService;
        this.trailService = trailService;
    }

    @GetMapping("/all")
    public List<Tag> getAllTags() {
        return tagService.findAll();
    }

    @GetMapping("get/{tagId}")
    public Tag getTag(@PathVariable Long tagId) {
        Tag result = tagService.findTagById(tagId);
        if (result == null) {
            throw new TagNotFoundException(tagId);
        }
        return result;
    }


    @GetMapping("/delete/{tagId}")
    public String deleteTag(@PathVariable Long tagId) {
        tagService.deleteTagById(tagId);
        return "Tag number " + tagId + " deleted";
    }

    @PostMapping("/add-post")
    public ResponseEntity<?> addTag(@Valid @RequestBody TagDTO tagRequest) {

        String tagName = tagRequest.getTagName();
        String tagDescription = tagRequest.getTagDescription();

        // szukanie Trails przez ID podane w request
        List<Trail> trailsForTag = new ArrayList<>();

        for (Long id : tagRequest.getTagTrailIds()) {
            Trail trail = trailService.findTrailById(id);
            trailsForTag.add(trail);
        }

        Tag newTag = new Tag();
        newTag.setName(tagName);
        newTag.setDescription(tagDescription);
        newTag.setTrails(trailsForTag);
        tagService.save(newTag);

        for (Trail trail : trailsForTag) {
            if (trail.getTags() == null) {
                trail.setTags(new ArrayList<>());
            }
            trail.getTags().add(newTag);
            trailService.addTrail(trail);
        }

        return ResponseEntity.ok("Tag added via post");
    }

    @PostMapping("/update-post/{tagId}")
    public ResponseEntity<?> updateTag(@Valid
                                           @PathVariable Long tagId,
                                       @RequestBody TagDTO tagRequest) {

        Tag tagToUpdate = tagService.findTagById(tagId);

        if (tagToUpdate == null) {
            throw new TagNotFoundException(tagId);
        }

        tagToUpdate.setName(tagRequest.getTagName());
        tagToUpdate.setDescription(tagRequest.getTagDescription());

        List<Trail> trailsForTag = new ArrayList<>();
        for (Long id : tagRequest.getTagTrailIds()) {
            Trail trail = trailService.findTrailById(id);
            trailsForTag.add(trail);
        }

        // poprzednia lista Trails dla tego taga
        // usuwanie tych trails z taga, których już nie ma w update
        List<Trail> previousTrails = tagToUpdate.getTrails() != null ? tagToUpdate.getTrails() : new ArrayList<>();
        for (Trail trail : previousTrails) {
            if (!trailsForTag.contains(trail)) {
                trail.getTags().remove(tagToUpdate);
                trailService.addTrail(trail);
            }
        }

        // dodanie nowych trails do taga
        for (Trail trail : trailsForTag) {
            if (trail.getTags() == null) {
                trail.setTags(new ArrayList<>());
            }
            if (!trail.getTags().contains(tagToUpdate)) {
                trail.getTags().add(tagToUpdate);
                trailService.addTrail(trail);
            }
        }

        tagToUpdate.setTrails(trailsForTag);
        tagService.save(tagToUpdate);

        return ResponseEntity.ok("Tag number " + tagId + " updated") ;
    }

    // Wyświetlenie wszystkich tagów dla danego Trail
    @GetMapping("/trails/{tagId}")
    public List<Trail> getTrailsForTag(@PathVariable Long tagId) {
        Tag tag = tagService.findTagById(tagId);
        return tag.getTrails();
    }

    // Znalezienie najdłuższego Trail dla podanego tagu
    @GetMapping("/longest-trail/{tagId}")
    public Trail getLongestTrailForTag(@PathVariable Long tagId) {
        return tagService.findLongestTrailByTagId(tagId);
    }

}
