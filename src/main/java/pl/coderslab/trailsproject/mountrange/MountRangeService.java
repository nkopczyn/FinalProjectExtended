package pl.coderslab.trailsproject.mountrange;

import org.springframework.stereotype.Service;

@Service
public class MountRangeService {
    private MountRangeRepository mountRangeRepository;

    public MountRangeService(MountRangeRepository mountRangeRepository) {
        this.mountRangeRepository = mountRangeRepository;
    }

    public MountRange getMountRangeByName(String name) {
        return mountRangeRepository.getMountRangeByName(name);
    }
}
