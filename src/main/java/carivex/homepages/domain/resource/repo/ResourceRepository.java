package carivex.homepages.domain.resource.repo;

import carivex.homepages.domain.resource.ResourceCategory;
import carivex.homepages.domain.resource.ResourcePost;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceRepository extends JpaRepository<ResourcePost, Long> {
    List<ResourcePost> findByCategory(ResourceCategory category, Sort sort);
}
