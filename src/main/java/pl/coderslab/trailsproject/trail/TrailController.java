package pl.coderslab.trailsproject.trail;

import org.springframework.web.bind.annotation.*;
import pl.coderslab.trailsproject.category.Category;
import pl.coderslab.trailsproject.category.CategoryService;
import pl.coderslab.trailsproject.mountrange.MountRange;
import pl.coderslab.trailsproject.mountrange.MountRangeService;
import pl.coderslab.trailsproject.point.Point;
import pl.coderslab.trailsproject.point.PointService;

import javax.print.DocFlavor;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @GetMapping("/summary")
    public String showTrailsSummary() {
        StringBuilder summary = new StringBuilder();

        List<Trail> trails = trailService.getAllTrails();

        // Trail::getLength - pobiera atrybut Length dla każdego trail
        double totalLength = trails.stream().mapToDouble(Trail::getLength).sum();
        List<String> distinctMountRanges = trails.stream().map(Trail::getMountRange).map(MountRange::getName)
                .distinct().collect(Collectors.toList());

        // build summary
        summary.append("Total number of trails: ").append(trails.size());
        summary.append("<br>");
        summary.append("Total length in km: ").append(totalLength);
        summary.append("<br>");
        summary.append("Mountain ranges mentioned: ").append(distinctMountRanges);

        return summary.toString();
    }

    @GetMapping("/get/{trailId}")
    public Trail getTrail(@PathVariable Long trailId) {
        return trailService.findTrailById(trailId);
    }

    @GetMapping("/delete/{trailId}")
    public String deleteTrail(@PathVariable Long trailId) {
        Trail trailToDel = trailService.findTrailById(trailId);
        trailService.deleteTrail(trailToDel);
        return "Trail number " + trailId + " deleted";
    }

    @PostMapping("/update-post-trail/{trailId}")
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


    @PostMapping("/add-post-trail")
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

    // find trails by category name, sorted from shortest to longest
    @GetMapping("/category/{catName}")
    public String showTrailsByCategory(@PathVariable String catName) {
        List<Trail> foundTrails = trailService.sortTrailsByCategory(catName);
        StringBuilder result = new StringBuilder();
        result.append("For category ").append(catName).append(" found ").append(foundTrails.size()).append(" trail(s): ");
        result.append("<br>");

        for(Trail t : foundTrails) {
            result.append("Trail number ").append(t.getId()).append(", ");
            result.append(t.getName()).append(", ");
            result.append(t.getLength()).append(" km");
            result.append("<br>");
        }

        return result.toString();
    }

}
