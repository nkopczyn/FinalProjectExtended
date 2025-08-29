package pl.coderslab.trailsproject.trail;

import org.springframework.stereotype.Service;
import pl.coderslab.trailsproject.category.Category;
import pl.coderslab.trailsproject.category.CategoryRepository;
import pl.coderslab.trailsproject.category.CategoryService;
import pl.coderslab.trailsproject.point.Point;

import java.util.List;

@Service
public class TrailService {
    private final TrailRepository trailRepository;
    private final CategoryService categoryService;

    public TrailService(TrailRepository trailRepository, CategoryService categoryService) {
        this.trailRepository = trailRepository;
        this.categoryService = categoryService;
    }


    public List<Trail> getAllTrails() {
        return trailRepository.findAll();
    }

    public void addTrail(Trail trail) {
        trailRepository.save(trail);
    }


    public double calculateTrailLength(Point start, Point finish) {
        double lat1 = Math.toRadians(start.getLatitude());
        double lon1 = Math.toRadians(start.getLongitude());
        double lat2 = Math.toRadians(finish.getLatitude());
        double lon2 = Math.toRadians(finish.getLongitude());

        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;

        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));
        double r = 6371; // promień Ziemi w km

        return c * r;
    }


    public Category determineTrailCategory(double length) {
        if (length < 5)
            return categoryService.findByIntensity("easy");
        else if (length < 15)
            return categoryService.findByIntensity("mid");
        else
            return categoryService.findByIntensity("hard");
    }


}
