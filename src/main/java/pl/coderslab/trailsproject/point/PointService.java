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
}
