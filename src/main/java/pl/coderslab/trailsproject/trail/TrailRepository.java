package pl.coderslab.trailsproject.trail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrailRepository extends JpaRepository<Trail, Long> {
}
