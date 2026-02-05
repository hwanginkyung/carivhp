package carivex.homepages.domain.resource.service;

import carivex.homepages.domain.resource.ResourceCategory;
import carivex.homepages.domain.resource.ResourcePost;
import carivex.homepages.domain.resource.repo.ResourceRepository;
import carivex.homepages.domain.translation.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final ResourceRepository resourceRepository;
    private final TranslationService translationService;

    @Transactional(readOnly = true)
    public List<ResourcePost> listAll() {
        return resourceRepository.findAll(Sort.by(Sort.Order.desc("pinned"), Sort.Order.desc("id")));
    }

    @Transactional(readOnly = true)
    public Page<ResourcePost> listAll(Pageable pageable) {
        return resourceRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<ResourcePost> listByCategory(ResourceCategory category) {
        return resourceRepository.findByCategory(category, Sort.by(Sort.Order.desc("pinned"), Sort.Order.desc("id")));
    }

    @Transactional(readOnly = true)
    public Page<ResourcePost> listByCategory(ResourceCategory category, Pageable pageable) {
        return resourceRepository.findByCategory(category, pageable);
    }

    @Transactional(readOnly = true)
    public ResourcePost get(Long id) {
        return resourceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Resource not found: " + id));
    }

    @Transactional(readOnly = true)
    public java.util.Optional<ResourcePost> find(Long id) {
        return resourceRepository.findById(id);
    }

    @Transactional
    public ResourcePost create(ResourceCategory category, String title, String content,
                               String fileOriginal, String fileStored) {
        String titleEn = translationService.translateToEnglishText(title);
        String contentEn = translationService.translateToEnglishHtml(content);
        return resourceRepository.save(
                new ResourcePost(category, title, content, titleEn, contentEn, fileOriginal, fileStored)
        );
    }

    @Transactional
    public ResourcePost update(Long id, ResourceCategory category, String title, String content,
                               String fileOriginal, String fileStored) {
        ResourcePost p = get(id);
        String titleEn = translationService.translateToEnglishText(title);
        String contentEn = translationService.translateToEnglishHtml(content);
        p.update(category, title, content, titleEn, contentEn, fileOriginal, fileStored);
        return p;
    }

    @Transactional
    public ResourcePost togglePinned(Long id) {
        ResourcePost p = get(id);
        p.setPinned(!p.isPinned());
        return p;
    }

    @Transactional
    public void delete(Long id) {
        resourceRepository.deleteById(id);
    }
}
