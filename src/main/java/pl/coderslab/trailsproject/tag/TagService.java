package pl.coderslab.trailsproject.tag;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public void deleteTagById(Long tagId) {
        Tag tag = tagRepository.findById(tagId).orElse(null);
        assert tag != null;
        for (Trail trail : tag.getTrails()) {
            trail.getTags().remove(tag);
            trailRepository.save(trail);
        }
        tagRepository.deleteById(tagId);
    }

    public Trail findLongestTrailByTagId(Long id) {
        return trailRepository.findFirstByTags_IdOrderByLengthDesc(id);
    }

}
