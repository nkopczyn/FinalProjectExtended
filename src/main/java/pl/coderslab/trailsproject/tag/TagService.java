package pl.coderslab.trailsproject.tag;

import org.springframework.stereotype.Service;
import pl.coderslab.trailsproject.trail.Trail;
import pl.coderslab.trailsproject.trail.TrailRepository;

import java.util.List;

@Service
public class TagService {
    private final TagRepository tagRepository;
    private final TrailRepository trailRepository;

    public TagService(TagRepository tagRepository, TrailRepository trailRepository) {
        this.tagRepository = tagRepository;
        this.trailRepository = trailRepository;
    }

    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    public void save(Tag tag) {
        tagRepository.save(tag);
    }

    public Tag findTagById(Long id) {
        return tagRepository.findById(id).orElse(null);
    }

    public void deleteTagById(Long id) {
        tagRepository.deleteById(id);
    }

    public Trail findLongestTrailByTagId(Long id) {
        return trailRepository.findFirstByTags_IdOrderByLengthDesc(id);
    }

}
