package pl.coderslab.trailsproject.category;

import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category findByIntensity(String intensity) {
        return categoryRepository.findByIntensity(intensity);
    }


}
