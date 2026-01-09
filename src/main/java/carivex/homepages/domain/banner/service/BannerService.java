package carivex.homepages.domain.banner.service;

import carivex.homepages.domain.banner.Banner;

import carivex.homepages.domain.banner.repo.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepository bannerRepository;

    @Transactional(readOnly = true)
    public List<Banner> listAll() {
        return bannerRepository.findAllSorted();
    }

    @Transactional(readOnly = true)
    public Banner get(Long id) {
        return bannerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Banner not found: " + id));
    }

    @Transactional
    public Banner create(String title, String linkUrl, String imageOriginal, String imageStored, boolean active, int sortOrder) {
        return bannerRepository.save(new Banner(title, linkUrl, imageOriginal, imageStored, active, sortOrder));
    }

    @Transactional
    public Banner update(Long id, String title, String linkUrl, String imageOriginal, String imageStored, boolean active, int sortOrder) {
        Banner b = get(id);
        b.update(title, linkUrl, imageOriginal, imageStored, active, sortOrder);
        return b;
    }

    @Transactional
    public void delete(Long id) {
        bannerRepository.deleteById(id);
    }
}
