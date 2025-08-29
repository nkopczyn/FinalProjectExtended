package pl.coderslab.trailsproject.trail;

import org.springframework.web.bind.annotation.*;
import pl.coderslab.trailsproject.category.Category;
import pl.coderslab.trailsproject.category.CategoryService;
import pl.coderslab.trailsproject.mountrange.MountRange;
import pl.coderslab.trailsproject.mountrange.MountRangeService;
import pl.coderslab.trailsproject.point.Point;
import pl.coderslab.trailsproject.point.PointService;

import java.util.List;

@RestController
@RequestMapping("/trails")
public class TrailController {
    private final TrailService trailService;
    private final CategoryService categoryService;
    private final PointService pointService;
    private final MountRangeService mountRangeService;

    public TrailController(TrailService trailService, CategoryService categoryService,
                           PointService pointService, MountRangeService mountRangeService) {
        this.trailService = trailService;
        this.categoryService = categoryService;
        this.pointService = pointService;
        this.mountRangeService = mountRangeService;
    }

    @GetMapping("/all")
    public List<Trail> showTrails() {
        return trailService.getAllTrails();
    }


    @PostMapping("/add-post")
    public String addTrail(@RequestBody TrailDTO trailRequest) {
        // Pobierz punkty startowy i końcowy na podstawie przesłanych ID
        Point start = pointService.getPointById(trailRequest.getStartPointId());
        Point finish = pointService.getPointById(trailRequest.getFinishPointId());

        // trail name add
        String trailName = trailRequest.getTrailName();
        MountRange mountRange = mountRangeService.getMountRangeByName(trailRequest.getMountRangeName());

        // Oblicz długość trasy na podstawie współrzędnych punktów
        double length = trailService.calculateTrailLength(start, finish);

        // Ustal kategorię na podstawie długości
        Category category = trailService.determineTrailCategory(length);

        Trail trail = new Trail();
        trail.setName(trailName);
        trail.setStart(start);
        trail.setFinish(finish);
        trail.setLength(length);
        trail.setCategory(category);
        trail.setMountRange(mountRange);

        trailService.addTrail(trail);

        return "Trail added via post";
    }

}
