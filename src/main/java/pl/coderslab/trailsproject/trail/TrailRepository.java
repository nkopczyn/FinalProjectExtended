package pl.coderslab.trailsproject.trail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrailRepository extends JpaRepository<Trail, Long> {

    public List<Trail> findByCategory_IntensityOrderByLengthAsc(String intensity);

    Trail findFirstByTags_IdOrderByLengthDesc(Long tagId);

}
