package pl.coderslab.trailsproject.trail;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.trailsproject.TrailNotFoundException;
import pl.coderslab.trailsproject.category.Category;
import pl.coderslab.trailsproject.category.CategoryService;
import pl.coderslab.trailsproject.mountrange.MountRange;
import pl.coderslab.trailsproject.mountrange.MountRangeService;
import pl.coderslab.trailsproject.point.Point;
import pl.coderslab.trailsproject.point.PointService;

import java.util.List;
import java.util.stream.Collectors;


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
        double averageLength = totalLength / trails.size();
        List<String> distinctMountRanges = trails.stream().map(Trail::getMountRange).map(MountRange::getName)
                .distinct().collect(Collectors.toList());

        int countEasy = 0;
        int countMedium = 0;
        int countHard = 0;

        List<String> categoriesCount = trails.stream().map(Trail::getCategory).map(Category::getIntensity).collect(Collectors.toList());
        for (String cat : categoriesCount) {
            if (cat.equals("easy")) {
                countEasy++;
            } else if (cat.equals("medium")) {
                countMedium++;
            } else if (cat.equals("hard")) {
                countHard++;
            }
        }

        // build summary
        summary.append("Total number of trails: ").append(trails.size());
        summary.append("<br>");
        summary.append("Total length in km: ").append(totalLength);
        summary.append("<br>");
        summary.append("Average length in km: ").append(averageLength);
        summary.append("<br>");
        summary.append("Mountain ranges mentioned: ").append(distinctMountRanges);
        summary.append("<br>");
        summary.append("Number of trails for each category: ");
        summary.append("<br>");
        summary.append("hard: ").append(countHard);
        summary.append("<br>");
        summary.append("mid: ").append(countMedium);
        summary.append("<br>");
        summary.append("easy: ").append(countEasy);


        return summary.toString();
    }

    @GetMapping("/get/{trailId}")
    public Trail getTrail(@PathVariable Long trailId) {
        Trail result = trailService.findTrailById(trailId);
        if (result == null) {
            throw new TrailNotFoundException(trailId);
        }
        return result;
    }

    @GetMapping("/delete/{trailId}")
    public String deleteTrail(@PathVariable Long trailId) {
        Trail trailToDel = trailService.findTrailById(trailId);
        trailService.deleteTrail(trailToDel);
        return "Trail number " + trailId + " deleted";
    }

    @PostMapping("/update-post/{trailId}")
    public ResponseEntity<?> updateTrail(@PathVariable Long trailId,
                              @RequestBody TrailDTO trailRequest) {
        Trail trailToUpdate = trailService.findTrailById(trailId);

        if (trailToUpdate == null) {
            throw new TrailNotFoundException(trailId);
        }

        Point start = pointService.getOrCreatePoint(trailRequest.getStartPoint());
        Point end = pointService.getOrCreatePoint(trailRequest.getEndPoint());

        MountRange mountRange = mountRangeService.getMountRangeByName(trailRequest.getMountRangeName());
        double length = trailService.calculateTrailLength(start, end);
        Category category = trailService.determineTrailCategory(length);


        trailToUpdate.setName(trailRequest.getTrailName());
        trailToUpdate.setStart(start);
        trailToUpdate.setFinish(end);
        trailToUpdate.setMountRange(mountRange);
        trailToUpdate.setLength(length);
        trailToUpdate.setCategory(category);

        // ustawienie atrybutów
        trailService.updateTrail(trailToUpdate);

        return ResponseEntity.ok("Trail number " + trailId + " updated");
    }


    @PostMapping("/add-post")
    public ResponseEntity<?> addTrail(@Valid @RequestBody TrailDTO trailRequest) {

        Point start = pointService.getOrCreatePoint(trailRequest.getStartPoint());
        Point end = pointService.getOrCreatePoint(trailRequest.getEndPoint());

        String trailName = trailRequest.getTrailName();
        MountRange mountRange = mountRangeService.getOrCreateMountRange(trailRequest.getMountRangeName());

        double length = trailService.calculateTrailLength(start, end);
        Category category = trailService.determineTrailCategory(length);

        // ustawianie atrybutów
        Trail trail = new Trail();
        trail.setName(trailName);
        trail.setStart(start);
        trail.setFinish(end);
        trail.setLength(length);
        trail.setCategory(category);
        trail.setMountRange(mountRange);

        trailService.addTrail(trail);

        return ResponseEntity.ok("Trail added via post");
    }

    // Wyświetli wszytskie szlaki w danej kategorii od najkrótszego do najdłuższego
    @GetMapping("/category/{catName}")
    public List<Trail> showTrailsByCategory(@PathVariable String catName) {
        List<Trail> foundTrails = trailService.sortTrailsByCategory(catName);
        return foundTrails;
    }

}
