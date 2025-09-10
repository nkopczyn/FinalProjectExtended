package pl.coderslab.trailsproject;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ViewExceptionHandler {

    @ExceptionHandler({TagNotFoundException.class,
            TrailNotFoundException.class,
            NoTrailsAvailableException.class})
    public String handleNotFound(RuntimeException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error-view";
    }
}
