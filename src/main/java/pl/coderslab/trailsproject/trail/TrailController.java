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

    @GetMapping("/delete/{trailId}")
    public String deleteTrail(@PathVariable Long trailId) {
        Trail trailToDel = trailService.findTrailById(trailId);
        trailService.deleteTrail(trailToDel);
        return "Trail number " + trailId + " deleted";
    }

    @PostMapping("/update-post/{trailId}")
    public String updateTrail(@PathVariable Long trailId,
                              @RequestBody TrailDTO trailRequest) {
        Trail trailToUpdate = trailService.findTrailById(trailId);

        if (trailToUpdate == null) {
            return "Trail not found";
        }

        Point start = pointService.getPointById(trailRequest.getStartPointId());
        Point finish = pointService.getPointById(trailRequest.getFinishPointId());

        MountRange mountRange = mountRangeService.getMountRangeByName(trailRequest.getMountRangeName());
        double length = trailService.calculateTrailLength(start, finish);
        Category category = trailService.determineTrailCategory(length);


        trailToUpdate.setName(trailRequest.getTrailName());
        trailToUpdate.setStart(start);
        trailToUpdate.setFinish(finish);
        trailToUpdate.setMountRange(mountRange);
        trailToUpdate.setLength(length);
        trailToUpdate.setCategory(category);

        // ustawienie atrybutów
        trailService.updateTrail(trailToUpdate);

        return "Trail number " + trailId + " updated";
    }


    @PostMapping("/add-post")
    public String addTrail(@RequestBody TrailDTO trailRequest) {

        // pobieranie atrybutów
        Point start = pointService.getPointById(trailRequest.getStartPointId());
        Point finish = pointService.getPointById(trailRequest.getFinishPointId());
        String trailName = trailRequest.getTrailName();
        MountRange mountRange = mountRangeService.getMountRangeByName(trailRequest.getMountRangeName());

        // category +  length obliczanie
        double length = trailService.calculateTrailLength(start, finish);
        Category category = trailService.determineTrailCategory(length);

        // ustawianie atrybutów
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
