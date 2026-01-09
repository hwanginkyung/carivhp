package carivex.homepages.domain.resource.service;

import carivex.homepages.domain.resource.ResourceCategory;
import carivex.homepages.domain.resource.ResourcePost;
import carivex.homepages.domain.resource.repo.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final ResourceRepository resourceRepository;

    @Transactional(readOnly = true)
    public List<ResourcePost> listAll() {
        return resourceRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Transactional(readOnly = true)
    public List<ResourcePost> listByCategory(ResourceCategory category) {
        return resourceRepository.findByCategory(category, Sort.by(Sort.Direction.DESC, "id"));
    }

    @Transactional(readOnly = true)
    public ResourcePost get(Long id) {
        return resourceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Resource not found: " + id));
    }

    @Transactional
    public ResourcePost create(ResourceCategory category, String title, String content,
                               String fileOriginal, String fileStored) {
        return resourceRepository.save(new ResourcePost(category, title, content, fileOriginal, fileStored));
    }

    @Transactional
    public ResourcePost update(Long id, ResourceCategory category, String title, String content,
                               String fileOriginal, String fileStored) {
        ResourcePost p = get(id);
        p.update(category, title, content, fileOriginal, fileStored);
        return p;
    }

    @Transactional
    public void delete(Long id) {
        resourceRepository.deleteById(id);
    }
}
