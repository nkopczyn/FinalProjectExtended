package pl.coderslab.trailsproject.trail;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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


@Controller
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
    public String showTrails(Model model) {
        List<Trail> trails = trailService.getAllTrails();
        model.addAttribute("trails", trails);
        return "trail-list";
    }

    @GetMapping("/summary")
    public String showTrailsSummary(Model model) {
        List<Trail> trails = trailService.getAllTrails();

        // Trail::getLength - pobiera atrybut Length dla każdego trail
        int numberOfTrails = trails.size();
        double totalLength = Math.round(trails.stream().mapToDouble(Trail::getLength).sum());
        double averageLength = Math.round(totalLength / trails.size());
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

        model.addAttribute("numberOfTrails", numberOfTrails);
        model.addAttribute("totalLength", totalLength);
        model.addAttribute("averageLength", averageLength);
        model.addAttribute("distinctMountRanges", distinctMountRanges);

        model.addAttribute("countEasy", countEasy);
        model.addAttribute("countMedium", countMedium);
        model.addAttribute("countHard", countHard);

        return "trail-summary";
    }

    @GetMapping("/get/{trailId}")
    public String getTrail(@PathVariable Long trailId,
                          Model model) {
        Trail result = trailService.findTrailById(trailId);
        if (result == null) {
            throw new TrailNotFoundException(trailId);
        }
        model.addAttribute("result", result);
        return "trail-display-id";
    }

    @GetMapping("/delete/{trailId}")
    public String deleteTrail(@PathVariable Long trailId,
                              Model model) {
        Trail trailToDel = trailService.findTrailById(trailId);
        trailService.deleteTrail(trailToDel);
        model.addAttribute("trailId", trailId);
        return "trail-delete";
    }

    @GetMapping("/find-by-id")
    public String findTrailById(@RequestParam Long trailId) {
        return "redirect:/trails/get/" + trailId;
    }

//    @PostMapping("/update-post/{trailId}")
//    public ResponseEntity<?> updateTrail(@PathVariable Long trailId,
//                              @RequestBody TrailDTO trailRequest) {
//        Trail trailToUpdate = trailService.findTrailById(trailId);
//
//        if (trailToUpdate == null) {
//            throw new TrailNotFoundException(trailId);
//        }
//
//        Point start = pointService.getOrCreatePoint(trailRequest.getStartPoint());
//        Point end = pointService.getOrCreatePoint(trailRequest.getEndPoint());
//
//        MountRange mountRange = mountRangeService.getMountRangeByName(trailRequest.getMountRangeName());
//        double length = trailService.calculateTrailLength(start, end);
//        Category category = trailService.determineTrailCategory(length);
//
//
//        trailToUpdate.setName(trailRequest.getTrailName());
//        trailToUpdate.setStart(start);
//        trailToUpdate.setFinish(end);
//        trailToUpdate.setMountRange(mountRange);
//        trailToUpdate.setLength(length);
//        trailToUpdate.setCategory(category);
//
//        // ustawienie atrybutów
//        trailService.updateTrail(trailToUpdate);
//
//        return ResponseEntity.ok("Trail number " + trailId + " updated");
//    }
//
//
//    @PostMapping("/add-post")
//    public ResponseEntity<?> addTrail(@Valid @RequestBody TrailDTO trailRequest) {
//
//        Point start = pointService.getOrCreatePoint(trailRequest.getStartPoint());
//        Point end = pointService.getOrCreatePoint(trailRequest.getEndPoint());
//
//        String trailName = trailRequest.getTrailName();
//        MountRange mountRange = mountRangeService.getOrCreateMountRange(trailRequest.getMountRangeName());
//
//        double length = trailService.calculateTrailLength(start, end);
//        Category category = trailService.determineTrailCategory(length);
//
//        // ustawianie atrybutów
//        Trail trail = new Trail();
//        trail.setName(trailName);
//        trail.setStart(start);
//        trail.setFinish(end);
//        trail.setLength(length);
//        trail.setCategory(category);
//        trail.setMountRange(mountRange);
//
//        trailService.addTrail(trail);
//
//        return ResponseEntity.ok("Trail added via post");
//    }

    @PostMapping("/add-form")
    public String addTrailFromForm (@ModelAttribute("trailDTO") @Valid TrailDTO trailRequest,
                                    BindingResult bindingResult,
                                    Model model) {

        if (bindingResult.hasErrors()) {
            return "trail-add"; // jesli sa bledy w formularzu
        }

        // funkcja z PointService która zamienia objekty PointDTO nan Point
        Point start = pointService.convertToPoint(trailRequest.getStartPoint());
        Point end = pointService.convertToPoint(trailRequest.getEndPoint());

        start = pointService.getOrCreatePoint(start);
        end = pointService.getOrCreatePoint(end);

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

        return "redirect:/trails/all";
    }

    @GetMapping("/add-form")
    public String showAddTrailForm(Model model) {
        model.addAttribute("trailDTO", new TrailDTO());
        return "trail-add"; // nazwa widoku z formularzem
    }



    // Wyświetli wszytskie szlaki w danej kategorii od najkrótszego do najdłuższego
    @GetMapping("/category/{catName}")
    public String showTrailsByCategory(@PathVariable String catName,
                                            Model model) {
        List<Trail> foundTrails = trailService.sortTrailsByCategory(catName);
        model.addAttribute("trails", foundTrails);
        model.addAttribute("category", catName);
        return "trail-incategory";
    }

}
