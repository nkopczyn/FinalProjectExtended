package pl.coderslab.trailsproject.mountrange;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MountRangeRepository extends JpaRepository<MountRange, Long> {
    MountRange getMountRangeByName(String name);
}
