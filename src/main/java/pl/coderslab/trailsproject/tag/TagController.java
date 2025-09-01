package pl.coderslab.trailsproject.tag;

import org.springframework.web.bind.annotation.*;
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
        return tagService.findTagById(tagId);
    }

    @GetMapping("/delete/{tagId}")
    public String deleteTag(@PathVariable Long tagId) {
        tagService.deleteTagById(tagId);
        return "Tag number " + tagId + " deleted";
    }

    @PostMapping("/add-post-tag")
    public String addTag(@RequestBody TagDTO tagRequest) {

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

        return "Tag added";
    }

    @PostMapping("/update-post-tag/{tagId}")
    public String updateTag(@PathVariable Long tagId,
                            @RequestBody TagDTO tagRequest) {

        Tag tagToUpdate = tagService.findTagById(tagId);
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

        return "Tag updated";
    }




}
