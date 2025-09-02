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

    // sprawdzanie czy pasmo istnieje, jeśli nie, dodaje je
    public MountRange getOrCreateMountRange(String mountRangeName) {
        MountRange existing = mountRangeRepository.getMountRangeByName(mountRangeName);
        if (existing != null) {
            return existing;
        }
        MountRange newMountRange = new MountRange();
        newMountRange.setName(mountRangeName);
        return mountRangeRepository.save(newMountRange);
    }
}
