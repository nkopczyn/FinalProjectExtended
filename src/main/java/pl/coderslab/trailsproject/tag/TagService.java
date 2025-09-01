package pl.coderslab.trailsproject.tag;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
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

}
