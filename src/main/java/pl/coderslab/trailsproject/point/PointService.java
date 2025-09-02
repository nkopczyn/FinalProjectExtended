package pl.coderslab.trailsproject.point;

import org.springframework.stereotype.Service;

@Service
public class PointService {

    private final PointRepository pointRepository;

    public PointService(PointRepository pointRepository) {
        this.pointRepository = pointRepository;

    }

    public Point getPointById(Long id) {
        return pointRepository.findById(id).orElse(null);
    }

    public void addPoint(Point point) {
        pointRepository.save(point);
    }

    public Point findByLatitudeAndLongitude(double latitude, double longitude) {
        return pointRepository.findFirstByLatitudeAndLongitude(latitude, longitude);
    }

    // sprawdzanie czy punkt istnieje, jeśli nie, dodaje go
    public Point getOrCreatePoint(Point point) {
        Point existing = pointRepository.findFirstByLatitudeAndLongitude(point.getLatitude(), point.getLongitude());
        if (existing != null) {
            return existing;
        }
        return pointRepository.save(point);
    }

}
